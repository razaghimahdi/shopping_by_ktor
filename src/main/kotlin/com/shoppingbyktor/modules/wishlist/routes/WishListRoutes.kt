package com.shoppingbyktor.modules.wishlist.routes

import com.shoppingbyktor.modules.wishlist.controller.WishListController
import com.shoppingbyktor.database.models.WisListRequest

import com.shoppingbyktor.utils.ApiResponse
import com.shoppingbyktor.utils.extension.apiResponse
import com.shoppingbyktor.utils.extension.currentUser
import com.shoppingbyktor.utils.extension.requiredParameters
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Route for managing the user's wish list operations.
 *
 * @param wishlistController The controller responsible for handling wish list operations.
 */
fun Route.wishListRoutes(wishlistController: WishListController) {
    route("product") {

        /**
         * POST request to add a product to the user's wish list.
         *
         * @param productId The ID of the product to add to the wish list.
         * @response A response indicating the success of adding the product to the wish list.
         */
        post({
            tags("Wish List")
            summary = "auth[customer]"
            request {
                body<WisListRequest>()
            }
            apiResponse()
        }) {
            val requestBody = call.receive<WisListRequest>()
            call.respond(
                ApiResponse.success(
                    wishlistController.addToWishList(call.currentUser().userId, requestBody.productId),
                    HttpStatusCode.OK
                )
            )
        }

        /**
         * GET request to retrieve the user's wish list.
         *
         * @param limit The maximum number of products to retrieve from the wish list.
         * @response A response containing the products in the user's wish list.
         */
        get("wishlist", {
            tags("Wish List")
            summary = "auth[customer]"
            request {
                queryParameter<Long>("category_id")
                queryParameter<Int>("per_page")
                queryParameter<Int>("page")
            }
            apiResponse()
        }) {
            val categoryId = call.parameters["category_id"]?.toLongOrNull()
            val (page, perPage) = call.requiredParameters("page", "per_page") ?: return@get
            val userId = call.currentUser().userId

            call.respond(
                ApiResponse.success(
                    wishlistController.getWishList(
                        userId = userId,
                        categoryId = categoryId,
                        page = page.toInt(),
                        perPage = perPage.toInt()
                    ),
                    HttpStatusCode.OK
                )
            )
        }

        /**
         * DELETE request to remove a product from the user's wish list.
         *
         * @param productId The ID of the product to remove from the wish list.
         * @response A response indicating the success of removing the product from the wish list.
         */
        delete({
            tags("Wish List")
            summary = "auth[customer]"
            request {
                queryParameter<String>("productId") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (productId) = call.requiredParameters("productId") ?: return@delete
            call.respond(
                ApiResponse.success(
                    wishlistController.removeFromWishList(call.currentUser().userId, productId.toLong()),
                    HttpStatusCode.OK
                )
            )
        }
    }
}