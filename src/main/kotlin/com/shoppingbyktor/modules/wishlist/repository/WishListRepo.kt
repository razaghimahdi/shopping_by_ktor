package com.shoppingbyktor.modules.wishlist.repository

import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.entities.WishList

interface WishListRepo {
    /**
     * Adds a product to the user's wish list.
     *
     * @param userId The unique identifier of the user.
     * @param productId The unique identifier of the product to add.
     * @return The updated wish list entry.
     */
    suspend fun addToWishList(userId: Long, productId: Long): WishList

    /**
     * Retrieves a list of products from the user's wish list.
     *
     * @param userId The unique identifier of the user.
     * @param limit The maximum number of products to return.
     * @return A list of products in the user's wish list.
     */
    suspend fun getWishList(userId: Long, limit: Int): List<Product>

    /**
     * Removes a product from the user's wish list.
     *
     * @param userId The unique identifier of the user.
     * @param productId The unique identifier of the product to remove.
     * @return The removed product.
     */
    suspend fun removeFromWishList(userId: Long, productId: Long): Product
}