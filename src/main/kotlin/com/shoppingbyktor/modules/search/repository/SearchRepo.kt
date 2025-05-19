package com.shoppingbyktor.modules.search.repository

import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.models.product.request.ProductRequest
import com.shoppingbyktor.database.models.product.request.ProductSearchRequest
import com.shoppingbyktor.database.models.product.request.ProductWithFilterRequest
import com.shoppingbyktor.database.models.product.request.UpdateProduct
import com.shoppingbyktor.database.models.search.FilterResponse

interface SearchRepo {
    /**
     * Searches for products based on query parameters.
     *
     * @param productSearchRequest .
     * @return A list of products matching the search.
     */
    suspend fun searchProduct(
        productSearchRequest: ProductSearchRequest
    ): List<Product>

    /**
     * filter fo Searches for products.
     *
     * @param productSearchRequest .
     * @return A FilterResponse.
     */
    suspend fun filter(): FilterResponse
}