package com.shoppingbyktor.modules.shop.repository

import com.shoppingbyktor.database.entities.Shop

interface ShopRepo {
    /**
     * Creates a new shop.
     *
     * @param userId The unique identifier of the user creating the shop.
     * @param categoryId The unique identifier of the shop category.
     * @param name The name of the shop.
     * @return The created shop.
     */
    suspend fun createShop(userId: Long, categoryId: Long, name: String): Shop

    /**
     * Retrieves a list of shops with a limit.
     *
     * @param userId The unique identifier of the user.
     * @param limit The maximum number of shops to return.
     * @return A list of shops.
     */
    suspend fun getShops(userId: Long, limit: Int): List<Shop>

    /**
     * Updates an existing shop.
     *
     * @param userId The unique identifier of the user.
     * @param shopId The unique identifier of the shop to update.
     * @param name The updated name of the shop.
     * @return The updated shop.
     */
    suspend fun updateShop(userId: Long, shopId: Long, name: String): Shop

    /**
     * Deletes a shop.
     *
     * @param userId The unique identifier of the user.
     * @param shopId The unique identifier of the shop to delete.
     * @return A confirmation message.
     */
    suspend fun deleteShop(userId: Long, shopId: Long): String
}