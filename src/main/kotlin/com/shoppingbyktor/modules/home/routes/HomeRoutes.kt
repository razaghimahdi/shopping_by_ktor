package com.shoppingbyktor.modules.home.routes

import com.shoppingbyktor.database.models.product.request.ProductSearchRequest
import com.shoppingbyktor.database.models.product.request.ProductWithFilterRequest
import com.shoppingbyktor.modules.home.controller.HomeController
import com.shoppingbyktor.modules.product.controller.ProductController
import com.shoppingbyktor.utils.ApiResponse
import com.shoppingbyktor.utils.extension.apiResponse
import com.shoppingbyktor.utils.extension.requiredParameters
import io.github.smiley4.ktoropenapi.get
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


/**
 * Defines routes for managing home screen.
 *
 * @param homeController The controller handling home-related operations.
 */
fun Route.homeRoutes(homeController: HomeController) {
    // Main route for product management
    authenticate("jwt") {
        route("home") {

            /**
             * GET request to retrieve home details.
             */
            get({
                tags("home screen")
                apiResponse()
            }) {
                call.respond(ApiResponse.success(homeController.home(), HttpStatusCode.OK))
            }
        }
    }
}