package com.shoppingbyktor.modules.productcategory.routes

import com.shoppingbyktor.modules.productcategory.controller.ProductCategoryController
import com.shoppingbyktor.utils.ApiResponse
import com.shoppingbyktor.utils.extension.apiResponse
import com.shoppingbyktor.utils.extension.requiredParameters
import io.github.smiley4.ktoropenapi.get
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Defines routes for managing product categories.
 *
 * Accessible by customers, sellers, and admins for viewing product categories.
 * Admins have additional permissions to create, update, and delete categories.
 *
 * @param productCategoryController The controller handling product category-related operations.
 */
fun Route.productCategoryRoutes(productCategoryController: ProductCategoryController) {

    /**
     * GET request to retrieve product categories.
     *
     * Accessible by customers, sellers, and admins.
     *
     * @param limit The number of categories to return.
     */
    get("product-category", {
        tags("Product Category")
        request {
            queryParameter<String>("limit") {
                required = true
            }
        }
        apiResponse()
    }) {
        val (limit) = call.requiredParameters("limit") ?: return@get
        call.respond(
            ApiResponse.success(
                productCategoryController.getCategories(
                    limit.toInt()
                ), HttpStatusCode.OK
            )
        )
    }
}