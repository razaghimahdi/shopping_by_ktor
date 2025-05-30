package com.shoppingbyktor.modules.shopcategory.controller

import com.shoppingbyktor.database.entities.ShopCategory
import com.shoppingbyktor.database.entities.ShopCategoryDAO
import com.shoppingbyktor.database.entities.ShopCategoryTable
import com.shoppingbyktor.modules.shopcategory.repository.ShopCategoryRepo
import com.shoppingbyktor.utils.extension.alreadyExistException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query

/**
 * Controller for managing shop categories. Provides methods to create, retrieve, update, and delete categories.
 */
class ShopCategoryController : ShopCategoryRepo {

    /**
     * Creates a new shop category. If a category with the same name already exists, an exception is thrown.
     *
     * @param name The name of the category to be created.
     * @return The created shop category.
     * @throws alreadyExistException If a category with the same name already exists.
     */
    override suspend fun createCategory(name: String): ShopCategory = query {
        val isExistShopCategory =
            ShopCategoryDAO.Companion.find { ShopCategoryTable.name eq name }.toList().singleOrNull()
        isExistShopCategory?.let {
            throw name.alreadyExistException()
        } ?: ShopCategoryDAO.Companion.new {
            this.name = name
        }.response()
    }

    /**
     * Retrieves a list of shop categories with a specified limit on the number of categories.
     *
     * @param limit The maximum number of categories to retrieve.
     * @return A list of shop categories.
     */
    override suspend fun getCategories(limit: Int): List<ShopCategory> = query {
        val shopCategories = ShopCategoryDAO.Companion.all().limit(limit)
        shopCategories.map {
            it.response()
        }
    }

    /**
     * Updates an existing shop category's name. If the category does not exist, an exception is thrown.
     *
     * @param categoryId The ID of the category to be updated.
     * @param name The new name of the category.
     * @return The updated shop category.
     * @throws categoryId.notFoundException If the category with the specified ID is not found.
     */
    override suspend fun updateCategory(categoryId: Long, name: String): ShopCategory = query {
        val isShopCategoryExist =
            ShopCategoryDAO.Companion.find { ShopCategoryTable.id eq categoryId }.toList().singleOrNull()
        isShopCategoryExist?.let {
            it.name = name
            it.response()
        } ?: throw categoryId.notFoundException()
    }

    /**
     * Deletes a shop category. If the category does not exist, an exception is thrown.
     *
     * @param categoryId The ID of the category to be deleted.
     * @return The ID of the deleted category.
     * @throws categoryId.notFoundException If the category with the specified ID is not found.
     */
    override suspend fun deleteCategory(categoryId: Long): String = query {
        val shopCategoryExist =
            ShopCategoryDAO.Companion.find { ShopCategoryTable.id eq categoryId }.toList().singleOrNull()
        shopCategoryExist?.let {
            it.delete()
            categoryId.toString()
        } ?: throw categoryId.notFoundException()
    }
}