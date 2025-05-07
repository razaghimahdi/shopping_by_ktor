package com.shoppingbyktor.modules.productsubcategory.routes

import com.shoppingbyktor.modules.productsubcategory.controller.ProductSubCategoryController
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
 * Defines routes for managing product subcategories.
 *
 * Available for customers, sellers, and admins with different levels of access.
 *
 * @param subCategoryController The controller handling product subcategory operations.
 */
fun Route.productSubCategoryRoutes(subCategoryController: ProductSubCategoryController) {

    /**
     * GET request to retrieve product subcategories by category ID.
     *
     * Accessible by customers, sellers, and admins.
     *
     * @param categoryId The category ID to filter subcategories.
     * @param limit The number of subcategories to retrieve.
     */
    get("product-subcategory", {
        tags("Product SubCategory")
        request {
            queryParameter<String>("categoryId") {
                required = true
            }
            queryParameter<String>("limit") {
                required = true
            }
        }
        apiResponse()
    }) {
        val (categoryId, limit) = call.requiredParameters("categoryId", "limit") ?: return@get
        call.respond(
            ApiResponse.success(
                subCategoryController.getProductSubCategory(categoryId, limit.toInt()), HttpStatusCode.OK
            )
        )
    }
}