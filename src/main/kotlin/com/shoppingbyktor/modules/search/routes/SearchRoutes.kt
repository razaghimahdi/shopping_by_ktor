package com.shoppingbyktor.modules.search.routes

import com.shoppingbyktor.modules.product.controller.ProductController
import com.shoppingbyktor.database.models.product.request.ProductSearchRequest
import com.shoppingbyktor.database.models.product.request.ProductWithFilterRequest
import com.shoppingbyktor.modules.search.controller.SearchController
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
 * Defines routes for searching products.
 *
 * @param searchController The controller handling product-related operations.
 */
fun Route.searchRoutes(searchController: SearchController) {
    // Main route for search management
    route("search") {


        /**
         * GET request to search for products by name with optional filters.
         *
         * @param limit The number of products to retrieve.
         * @param productName The name of the product to search.
         * @param categoryId Optional category filter.
         * @param maxPrice Optional maximum price filter.
         * @param minPrice Optional minimum price filter.
         */
        get({
            tags("Search")
            request {
                pathParameter<String>("title")
                pathParameter<Long>("min_price")
                pathParameter<Long>("max_price")
                pathParameter<String>("categories_id")
                pathParameter<Long>("page")
                pathParameter<String>("sort")
            }
            apiResponse()
        }) {
            val queryParams = ProductSearchRequest(
                title = call.parameters["title"],
                categoriesId = call.parameters["categories_id"],
                sort = call.parameters["sort"],
                maxPrice = call.parameters["max_price"]?.toLongOrNull(),
                minPrice = call.parameters["min_price"]?.toLongOrNull(),
                page = call.parameters["page"]?.toLongOrNull(),
            )
            call.respond(ApiResponse.success(searchController.searchProduct(queryParams), HttpStatusCode.OK))
        }



        /**
         * GET request to get filter of search for products by price and categories.
         */
        get("/filter",{
            tags("Filter")
            apiResponse()
        }) {
            call.respond(ApiResponse.success(searchController.filter(), HttpStatusCode.OK))
        }

    }
}