package com.shoppingbyktor.modules.cart.repository

import com.shoppingbyktor.database.entities.Cart
import com.shoppingbyktor.database.entities.Product

interface CartRepo {
    /**
     * Adds a product to the cart or updates its quantity if already present.
     *
     * @param userId The unique identifier of the user.
     * @param productId The unique identifier of the product.
     * @param quantity The quantity of the product to add.
     * @return The updated cart.
     */
    suspend fun createCart(userId: Long, productId: Long, quantity: Int): Cart

    /**
     * Retrieves all cart items for a user.
     *
     * @param userId The unique identifier of the user.
     * @param limit The maximum number of cart items to return.
     * @return A list of cart items.
     */
    suspend fun getCartItems(userId: Long, limit: Int): List<Cart>

    /**
     * Updates the quantity of a specific product in the cart.
     *
     * @param userId The unique identifier of the user.
     * @param productId The unique identifier of the product.
     * @param quantity The new quantity of the product.
     * @return The updated cart.
     */
    suspend fun updateCartQuantity(userId: Long, productId: Long, quantity: Int): Cart

    /**
     * Removes a specific product from the cart.
     *
     * @param userId The unique identifier of the user.
     * @param productId The unique identifier of the product.
     * @return The removed product.
     */
    suspend fun removeCartItem(userId: Long, productId: Long): Product

    /**
     * Clears all items from a user's cart.
     *
     * @param userId The unique identifier of the user.
     * @return `true` if the cart was cleared successfully, `false` otherwise.
     */
    suspend fun clearCart(userId: Long): Boolean
}