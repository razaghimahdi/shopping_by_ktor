package com.shoppingbyktor.modules.cart.controller

import com.shoppingbyktor.database.entities.Cart
import com.shoppingbyktor.database.entities.CartItemDAO
import com.shoppingbyktor.database.entities.CartItemTable
import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.entities.ProductDAO
import com.shoppingbyktor.database.entities.ProductTable
import com.shoppingbyktor.modules.cart.repository.CartRepo
import com.shoppingbyktor.utils.extension.alreadyExistException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and

/**
 * Controller for managing cart-related operations.
 */
class CartController : CartRepo {

    /**
     * Creates a new cart item for a user with the specified product and quantity.
     *
     * @param userId The ID of the user for whom the cart item is being created.
     * @param productId The ID of the product to be added to the cart.
     * @param quantity The quantity of the product being added to the cart.
     * @return The created cart item entity.
     * @throws Exception if the product already exists in the user's cart.
     */
    override suspend fun createCart(userId: Long, productId: Long, quantity: Int): Any? = query {
        val isProductExist =
            CartItemDAO.Companion.find { CartItemTable.userId eq userId and (CartItemTable.productId eq productId) }
                .toList().singleOrNull()
        isProductExist?.let {
            it.quantity = it.quantity + quantity
            ProductDAO.Companion.find { ProductTable.id eq it.productId }.first()
        } ?: CartItemDAO.Companion.new {
            this.userId = EntityID(userId, CartItemTable)
            this.productId = EntityID(productId, CartItemTable)
            this.quantity = quantity
        }

        return@query null
    }

    /**
     * Retrieves a list of cart items for a user, with a specified limit.
     *
     * @param userId The ID of the user for whom to retrieve cart items.
     * @return A list of cart item entities with associated product details.
     */
    override suspend fun getCartItems(userId: Long): List<Product> = query {
        CartItemDAO.Companion.find { CartItemTable.userId eq userId }.map {
            ProductDAO.Companion.find { ProductTable.id eq it.productId }.first().response(it.quantity)
        }
    }

    /**
     * Updates the quantity of an existing cart item.
     *
     * @param userId The ID of the user whose cart item quantity is to be updated.
     * @param productId The ID of the product for which the quantity is being updated.
     * @param quantity The amount to update the product quantity by.
     * @return The updated cart item entity with the new quantity.
     * @throws Exception if the product does not exist in the user's cart.
     */
    override suspend fun updateCartQuantity(userId: Long, productId: Long, quantity: Int): Cart = query {
        val isProductExist =
            CartItemDAO.Companion.find { CartItemTable.userId eq userId and (CartItemTable.productId eq productId) }
                .toList().singleOrNull()
        isProductExist?.let {
            it.quantity = it.quantity + quantity
            it.response(ProductDAO.Companion.find { ProductTable.id eq it.productId }.first().response())
        } ?: throw productId.notFoundException()
    }

    /**
     * Removes a product from a user's cart.
     *
     * @param userId The ID of the user from whose cart the product is to be removed.
     * @param productId The ID of the product to be removed from the cart.
     * @return The product entity that was removed from the cart.
     * @throws Exception if the product does not exist in the user's cart.
     */
    override suspend fun removeCartItem(userId: Long, productId: Long):  Any? = query {
        val isProductExist =
            CartItemDAO.Companion.find { CartItemTable.userId eq userId and (CartItemTable.productId eq productId) }
                .toList().singleOrNull()
        isProductExist?.let {
            it.delete()
            ProductDAO.Companion.find { ProductTable.id eq it.productId }.first()
        } ?: throw productId.notFoundException()

        return@query null
    }

    /**
     * Clears all items from a user's cart.
     *
     * @param userId The ID of the user whose cart is to be cleared.
     * @return True if the cart was cleared successfully, false if the cart was already empty.
     */
    override suspend fun clearCart(userId: Long): Boolean = query {
        val isCartExist = CartItemDAO.Companion.find { CartItemTable.userId eq userId }.toList()
        if (isCartExist.isEmpty()) {
            true
        } else {
            isCartExist.forEach {
                it.delete()
            }
            true
        }
    }
}