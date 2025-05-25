package com.shoppingbyktor.modules.wishlist.controller

import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.entities.ProductDAO
import com.shoppingbyktor.database.entities.ProductTable
import com.shoppingbyktor.database.entities.WishList
import com.shoppingbyktor.database.entities.WishListDAO
import com.shoppingbyktor.database.entities.WishListTable
import com.shoppingbyktor.modules.wishlist.repository.WishListRepo
import com.shoppingbyktor.utils.extension.alreadyExistException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import io.ktor.server.plugins.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.and

/**
 * Controller for managing the user's wishlist. Provides methods to add, retrieve, and remove items from the wishlist.
 */
class WishListController : WishListRepo {

    /**
     * Adds a product to the user's wishlist. If the product is already in the wishlist, then it will delete.
     *
     * @param userId The ID of the user who wants to add the product to their wishlist.
     * @param productId The ID of the product to be added to the wishlist.
     */
    override suspend fun addToWishList(userId: Long, productId: Long?): Any? = query {
        if (productId == null) throw NotFoundException("Product not found")
        val isProductExist =
            ProductDAO.Companion.find { ProductTable.id eq productId }.toList().singleOrNull()
        if (isProductExist == null) throw NotFoundException("Product not found")

        val isExits =
            WishListDAO.Companion.find { WishListTable.userId eq userId and (WishListTable.productId eq productId) }
                .toList()
                .singleOrNull()
        isExits?.delete()
            ?: WishListDAO.Companion.new {
                this.userId = EntityID(userId, WishListTable)
                this.productId = EntityID(productId, WishListTable)
            }
        null
    }

    /**
     * Retrieves the list of products in the user's wishlist with a specified limit on the number of items.
     *
     * @param userId The ID of the user whose wishlist is to be retrieved.
     * @param limit The maximum number of products to retrieve.
     * @return A list of products in the user's wishlist.
     */
    override suspend fun getWishList(
        userId: Long,
        categoryId: Long?,
        perPage: Int,
        page: Int
    ): List<Product> = query {
        val offset = (page - 1).coerceAtLeast(0) * perPage

        val wishlistProductIds = WishListDAO.find { WishListTable.userId eq userId }
            .map { it.productId.value }

        val conditions = mutableListOf<Op<Boolean>>(
            ProductTable.id inList wishlistProductIds
        )

        if (categoryId != null) {
            conditions += (ProductTable.categoryId eq categoryId)
        }

        ProductDAO.find(
            conditions.reduce { acc, op -> acc and op }
        )
            .limit(perPage).offset(start = offset.toLong())
            .map { it.response() }
    }


    /**
     * Removes a product from the user's wishlist. If the product is not found in the wishlist, then it will add.
     *
     * @param userId The ID of the user who wants to remove the product from their wishlist.
     * @param productId The ID of the product to be removed from the wishlist.
     */
    override suspend fun removeFromWishList(userId: Long, productId: Long): Any? = query {
        val isExits =
            WishListDAO.Companion.find { WishListTable.userId eq userId and (WishListTable.productId eq productId) }
                .toList()
                .singleOrNull()
        isExits?.let {
            it.delete()
            ProductDAO.Companion.find { ProductTable.id eq it.productId }.first().response()
        } ?: run {
            //  throw productId.notFoundException()
            WishListDAO.Companion.new {
                this.userId = EntityID(userId, WishListTable)
                this.productId = EntityID(productId, WishListTable)
            }
        }
    }
}