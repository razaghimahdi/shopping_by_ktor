package com.shoppingbyktor.modules.shopcategory.repository

import com.shoppingbyktor.database.entities.ShopCategory

interface ShopCategoryRepo {
    /**
     * Creates a new shop category.
     *
     * @param categoryName The name of the shop category.
     * @return The created shop category.
     */
    suspend fun createCategory(categoryName: String): ShopCategory

    /**
     * Retrieves a list of shop categories with a limit.
     *
     * @param limit The maximum number of categories to return.
     * @return A list of shop categories.
     */
    suspend fun getCategories(limit: Int): List<ShopCategory>

    /**
     * Updates an existing shop category.
     *
     * @param categoryId The unique identifier of the shop category.
     * @param categoryName The updated name of the shop category.
     * @return The updated shop category.
     */
    suspend fun updateCategory(categoryId: Long, categoryName: String): ShopCategory

    /**
     * Deletes a specific shop category.
     *
     * @param categoryId The unique identifier of the shop category to delete.
     * @return A confirmation message.
     */
    suspend fun deleteCategory(categoryId: Long): String
}