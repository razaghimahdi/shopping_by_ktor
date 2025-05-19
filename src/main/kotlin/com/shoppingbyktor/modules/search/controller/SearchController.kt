package com.shoppingbyktor.modules.search.controller

import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.entities.ProductCategoryDAO
import com.shoppingbyktor.database.entities.ProductDAO
import com.shoppingbyktor.database.entities.ProductTable
import com.shoppingbyktor.database.models.product.request.ProductRequest
import com.shoppingbyktor.database.models.product.request.ProductSearchRequest
import com.shoppingbyktor.database.models.product.request.ProductWithFilterRequest
import com.shoppingbyktor.database.models.product.request.UpdateProduct
import com.shoppingbyktor.database.models.search.FilterResponse
import com.shoppingbyktor.modules.product.repository.ProductRepo
import com.shoppingbyktor.modules.search.repository.SearchRepo
import com.shoppingbyktor.utils.AppConstants
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import java.io.File

/**
 * Controller for searching products.
 */
class SearchController : SearchRepo {


    /**
     * Searches for products based on the given search criteria.
     *
     * @param productQuery The search request containing the parameters for searching products.
     * @return A list of products matching the search criteria.
     */
    override suspend fun searchProduct(
        productSearchRequest: ProductSearchRequest
    ): List<Product> = query {
        val conditions = mutableListOf<Op<Boolean>>()


        if (!productSearchRequest.title.isNullOrEmpty()) {
            conditions.add(ProductTable.title like "%${productSearchRequest.title}%")
        }
        if (productSearchRequest.minPrice != null) {
            conditions.add(ProductTable.price greaterEq productSearchRequest.minPrice)
        }
        if (productSearchRequest.maxPrice != null) {
            conditions.add(ProductTable.price lessEq productSearchRequest.maxPrice)
        }
        if (!productSearchRequest.categoriesId.isNullOrEmpty()) {
            val categoryIds = productSearchRequest.categoriesId.split(",")
                .mapNotNull { it.trim().toLongOrNull() }

            if (categoryIds.isNotEmpty()) {
                conditions.add(ProductTable.categoryId inList categoryIds)
            }
        }

        val where = if (conditions.isEmpty()) Op.TRUE else conditions.reduce { acc, op -> acc and op }

        val pageSize = 20
        val offset = ((productSearchRequest.page ?: 1) - 1) * pageSize

        // Determine sort direction
        val order = when (productSearchRequest.sort?.lowercase()) {
            "asc" -> ProductTable.price to SortOrder.ASC
            "desc" -> ProductTable.price to SortOrder.DESC
            else -> null
        }

        val query = ProductDAO.find(where)

        val sorted = if (order != null) query.orderBy(order) else query

        sorted.limit(pageSize).offset(offset).map { it.response() }
    }

    override suspend fun filter(): FilterResponse = query{
        val categoriesEntities = ProductCategoryDAO.Companion.all()
        val categories = categoriesEntities.map {
            it.response()
        }

        FilterResponse(min_price = 1, max_price = 9999, categories = categories)
    }

}