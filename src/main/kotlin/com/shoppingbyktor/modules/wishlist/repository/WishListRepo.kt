package com.shoppingbyktor.modules.wishlist.repository

import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.entities.WishList

interface WishListRepo {
    /**
     * Adds a product to the user's wish list.
     *
     * @param productId The unique identifier of the product to add.
     * @return The updated wish list entry.
     */
    suspend fun addToWishList(userId: Long, productId: Long): Any?

    /**
     * Retrieves a list of products from the user's wish list.
     *
     * @param categoryId filter base on category.
     * @param perPage for paging.
     * @param page for paging.
     * @return A list of products in the user's wish list.
     */
    suspend fun getWishList(userId: Long, categoryId: Long?, perPage: Int, page: Int): List<Product>

    /**
     * Removes a product from the user's wish list.
     *
     * @param productId The unique identifier of the product to remove.
     * @return The removed product.
     */
    suspend fun removeFromWishList(userId: Long, productId: Long): Any?
}