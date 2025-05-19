package com.shoppingbyktor.modules.product.repository

import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.models.product.request.ProductRequest
import com.shoppingbyktor.database.models.product.request.ProductSearchRequest
import com.shoppingbyktor.database.models.product.request.ProductWithFilterRequest
import com.shoppingbyktor.database.models.product.request.UpdateProduct

interface ProductRepo {
    /**
     * Creates a new product.
     *
     * @param userId The unique identifier of the user creating the product.
     * @param productRequest The details of the product to create.
     * @return The created product.
     */
    suspend fun createProduct(userId: Long, productRequest: ProductRequest): Product

    /**
     * Updates an existing product.
     *
     * @param userId The unique identifier of the user updating the product.
     * @param productId The unique identifier of the product to update.
     * @param update The product details to update.
     * @return The updated product.
     */
    suspend fun updateProduct(userId: Long, productId: Long, updateProduct: UpdateProduct): Product

    /**
     * Retrieves a list of products based on filters.
     *
     * @param query The filters to apply when retrieving products.
     * @return A list of products matching the filters.
     */
    suspend fun getProducts(productQuery: ProductWithFilterRequest): List<Product>

    /**
     * Retrieves a specific product by its ID.
     *
     * @param userId The unique identifier of the user.
     * @param query The filters to apply when retrieving the product.
     * @return A list of products (even if only one product matches).
     */
    suspend fun getProductById(userId: Long, productQuery: ProductWithFilterRequest): List<Product>

    /**
     * Retrieves detailed information about a specific product.
     *
     * @param productId The unique identifier of the product.
     * @return The product details.
     */
    suspend fun getProductDetail(productId: Long): Product

    /**
     * Deletes a specific product.
     *
     * @param userId The unique identifier of the user deleting the product.
     * @param productId The unique identifier of the product to delete.
     * @return A confirmation message.
     */
    suspend fun deleteProduct(userId: Long, productId: Long): String

}