package com.shoppingbyktor.modules.shop.controller

import com.shoppingbyktor.database.entities.Shop
import com.shoppingbyktor.database.entities.ShopDAO
import com.shoppingbyktor.database.entities.ShopTable
import com.shoppingbyktor.database.entities.UserTable
import com.shoppingbyktor.modules.shop.repository.ShopRepo
import com.shoppingbyktor.utils.extension.alreadyExistException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and

/**
 * Controller for managing shops. Provides methods to create, retrieve, update, and delete shops.
 */
class ShopController : ShopRepo {

    /**
     * Creates a new shop. If a shop with the same name already exists, an exception is thrown.
     *
     * @param userId The ID of the user who owns the shop.
     * @param categoryId The ID of the category under which the shop falls.
     * @param name The name of the shop to be created.
     * @return The created shop.
     * @throws alreadyExistException If a shop with the same name already exists.
     */
    override suspend fun createShop(userId: Long, categoryId: Long, name: String): Shop = query {
        val shopNameExist = ShopDAO.Companion.find { ShopTable.name eq name }.toList().singleOrNull()
        if (shopNameExist == null) {
            ShopDAO.Companion.new {
                this.userId = EntityID(userId, UserTable)
                this.categoryId = EntityID(categoryId, ShopTable)
                this.name = name
            }.shopResponse()
        } else {
            throw name.alreadyExistException()
        }
    }

    /**
     * Retrieves a list of shops owned by a specific user with a specified limit on the number of shops.
     *
     * @param userId The ID of the user whose shops are to be retrieved.
     * @param limit The maximum number of shops to retrieve.
     * @return A list of shops owned by the user.
     */
    override suspend fun getShops(userId: Long, limit: Int): List<Shop> = query {
        val isExist = ShopDAO.Companion.find { ShopTable.userId eq userId }.limit(limit).toList()
        isExist.map {
            it.shopResponse()
        }
    }

    /**
     * Updates an existing shop's name. If the shop is not found, an exception is thrown.
     *
     * @param userId The ID of the user who owns the shop.
     * @param shopId The ID of the shop to be updated.
     * @param name The new name for the shop.
     * @return The updated shop.
     * @throws notFoundException If the shop with the specified ID is not found.
     */
    override suspend fun updateShop(userId: Long, shopId: Long, name: String): Shop = query {
        val shopNameExist =
            ShopDAO.Companion.find { ShopTable.userId eq userId and (ShopTable.id eq shopId) }.toList().singleOrNull()
        shopNameExist?.let {
            it.name = name
            it.shopResponse()
        } ?: throw name.notFoundException()
    }

    /**
     * Deletes a shop owned by a specific user. If the shop is not found, an exception is thrown.
     *
     * @param userId The ID of the user who owns the shop.
     * @param shopId The ID of the shop to be deleted.
     * @return The ID of the deleted shop.
     * @throws notFoundException If the shop with the specified ID is not found.
     */
    override suspend fun deleteShop(userId: Long, shopId: Long): String = query {
        val shopNameExist =
            ShopDAO.Companion.find { ShopTable.userId eq userId and (ShopTable.id eq shopId) }.toList().singleOrNull()
        shopNameExist?.let {
            it.delete()
            shopId.toString()
        } ?: throw shopId.notFoundException()
    }
}