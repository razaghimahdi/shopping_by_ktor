package com.shoppingbyktor.modules.brand.routes

import com.shoppingbyktor.modules.brand.controller.BrandController

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
 * Defines routes for managing brands, with authentication and authorization based on user roles.
 *
 * @param brandController The controller handling brand-related operations.
 */
fun Route.brandRoutes(brandController: BrandController) {
    route("brand") {
        /**
         * GET request to fetch a list of brands, with an optional limit on the number of brands.
         *
         * Accessible by customers, sellers, and admins.
         *
         * @param limit The maximum number of brands to return.
         */
             get({
                tags("Brand")
                summary = "auth[admin, customer, seller]"
                request {
                    queryParameter<Int>("limit") {
                        required = true
                    }
                }
                apiResponse()
            }) {
                val (limit) = call.requiredParameters("limit") ?: return@get
                call.respond(
                    ApiResponse.success(
                        brandController.getBrands(limit.toInt()), HttpStatusCode.OK
                    )
                )
         }

    }
}