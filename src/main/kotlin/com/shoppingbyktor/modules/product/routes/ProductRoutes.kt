package com.shoppingbyktor.modules.product.routes

import com.shoppingbyktor.modules.product.controller.ProductController
import com.shoppingbyktor.database.models.product.request.ProductSearchRequest
import com.shoppingbyktor.database.models.product.request.ProductWithFilterRequest
import com.shoppingbyktor.utils.ApiResponse
import com.shoppingbyktor.utils.extension.apiResponse
import com.shoppingbyktor.utils.extension.requiredParameters
import io.github.smiley4.ktoropenapi.get
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

/**
 * Defines routes for managing products. Different routes are available based on user roles (CUSTOMER, SELLER, ADMIN).
 *
 * @param productController The controller handling product-related operations.
 */
fun Route.productRoutes(productController: ProductController) {
    // Main route for product management
    route("product") {

        /**
         * GET request to retrieve product details by product ID.
         *
         * Accessible by customers, sellers, and admins.
         *
         * @param id The unique identifier of the product.
         */
        get("{id}", {
            tags("Product")
            request {
                pathParameter<String>("id") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (productId) = call.requiredParameters("id") ?: return@get
            call.respond(ApiResponse.success(productController.getProductDetail(productId.toLong()), HttpStatusCode.OK))
        }

        /**
         * GET request to retrieve a list of products with optional filters.
         *
         * Accessible by customers, sellers, and admins.
         *
         * @param limit The number of products to retrieve.
         * @param maxPrice Optional maximum price filter.
         * @param minPrice Optional minimum price filter.
         * @param categoryId Optional category filter.
         * @param subCategoryId Optional sub-category filter.
         * @param brandId Optional brand filter.
         */
        get({
            tags("Product")
            request {
                queryParameter<Int>("limit") {
                    required = true
                }
                queryParameter<Double>("maxPrice")
                queryParameter<Double>("minPrice")
                queryParameter<String>("categoryId")
                queryParameter<String>("subCategoryId")
                queryParameter<String>("brandId")
            }
            apiResponse()
        }) {
            val (limit) = call.requiredParameters("limit") ?: return@get
            val params = ProductWithFilterRequest(
                limit = limit.toInt(),
                maxPrice = call.parameters["maxPrice"]?.toLongOrNull(),
                minPrice = call.parameters["minPrice"]?.toLongOrNull(),
                categoryId = call.parameters["categoryId"]?.toLongOrNull(),
                subCategoryId = call.parameters["subCategoryId"]?.toLongOrNull(),
                brandId = call.parameters["brandId"]?.toLongOrNull()
            )
            call.respond(ApiResponse.success(productController.getProducts(params), HttpStatusCode.OK))
        }

    }
}