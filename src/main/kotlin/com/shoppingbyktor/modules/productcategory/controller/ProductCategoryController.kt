package com.shoppingbyktor.modules.productcategory.controller

import com.shoppingbyktor.database.entities.ProductCategory
import com.shoppingbyktor.database.entities.ProductCategoryDAO
import com.shoppingbyktor.database.entities.ProductCategoryTable
import com.shoppingbyktor.modules.productcategory.repository.ProductCategoryRepo
import com.shoppingbyktor.utils.extension.alreadyExistException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query

/**
 * Controller for managing product categories.
 */
class ProductCategoryController : ProductCategoryRepo {

    /**
     * Creates a new product category with the given category name.
     *
     * @param name The name of the category to be created.
     * @return The created product category entity.
     * @throws Exception if a category with the provided name already exists.
     */
    override suspend fun createCategory(name: String): ProductCategory = query {
        val isCategoryExist =
            ProductCategoryDAO.Companion.find { ProductCategoryTable.name eq name }.toList().singleOrNull()
        isCategoryExist?.let {
            throw name.alreadyExistException()
        } ?: ProductCategoryDAO.Companion.new {
            this.name = name
        }.response()
    }

    /**
     * Retrieves the list of product categories with a limit on the number of categories returned.
     *
     * @param limit The maximum number of categories to retrieve.
     * @return A list of product category entities.
     */
    override suspend fun getCategories(limit: Int?): List<ProductCategory> = query {
        val categories =
            if (limit != null) ProductCategoryDAO.Companion.all().limit(limit) else ProductCategoryDAO.Companion.all()
        categories.map {
            it.response()
        }
    }

    /**
     * Updates the name of an existing product category.
     *
     * @param categoryId The ID of the category to update.
     * @param categoryName The new name for the category.
     * @return The updated product category entity.
     * @throws Exception if no category is found with the provided category ID.
     */
    override suspend fun updateCategory(categoryId: Long, name: String): ProductCategory = query {
        val isCategoryExist =
            ProductCategoryDAO.Companion.find { ProductCategoryTable.id eq categoryId }.toList().singleOrNull()
        isCategoryExist?.let {
            it.name = name
            it.response()
        } ?: throw categoryId.notFoundException()
    }

    /**
     * Deletes an existing product category by its ID.
     *
     * @param categoryId The ID of the category to delete.
     * @return The ID of the deleted category.
     * @throws Exception if no category is found with the provided category ID.
     */
    override suspend fun deleteCategory(categoryId: Long): String = query {
        val isCategoryExist =
            ProductCategoryDAO.Companion.find { ProductCategoryTable.id eq categoryId }.toList().singleOrNull()
        isCategoryExist?.let {
            isCategoryExist.delete()
            categoryId.toString()
        } ?: throw categoryId.notFoundException()
    }
}