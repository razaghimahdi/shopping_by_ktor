package com.shoppingbyktor.modules.comment.routes

import com.shoppingbyktor.database.models.comment.CommentReviewRequest
import com.shoppingbyktor.modules.comment.controller.CommentReviewController
import com.shoppingbyktor.utils.ApiResponse
import com.shoppingbyktor.utils.extension.apiResponse
import com.shoppingbyktor.utils.extension.currentUser
import com.shoppingbyktor.utils.extension.requiredParameters
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.put
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Route for managing review and rating operations.
 *
 * @param reviewRatingController The controller responsible for handling review and rating-related operations.
 */
fun Route.commentReviewRoutes(reviewRatingController: CommentReviewController) {
    route("comment") {

        /**
         * GET request to retrieve reviews and ratings for a specific product.
         *
         * @param productId The ID of the product to get reviews and ratings.
         * @response A response containing the list of reviews and ratings for the product.
         */
        get({
            tags("Comment Review")
            request {
                queryParameter<String>("product_id") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (productId) = call.requiredParameters("product_id") ?: return@get
            call.respond(
                ApiResponse.success(
                    reviewRatingController.getCommentReview(productId.toLong()),
                    HttpStatusCode.OK
                )
            )
        }

        // Route for adding, updating, and deleting reviews and ratings

        // Route for posting a new review and rating
        /**
         * POST request to add a new review and rating for a product.
         *
         * @param requestBody The body of the request containing review and rating details.
         * @response A response indicating the success of the operation.
         */

        authenticate("jwt") {
            post({
                tags("Comment Review")
                summary = "auth[customer]"
                request {
                    body<CommentReviewRequest>()
                }
                apiResponse()
            }) {
                val requestBody = call.receive<CommentReviewRequest>()
                call.respond(
                    ApiResponse.success(
                        reviewRatingController.addCommentReview(call.currentUser().userId, requestBody),
                        HttpStatusCode.OK
                    )
                )
            }
        }

        // Route for updating an existing review and rating
        /**
         * PUT request to update an existing review and rating.
         *
         * @param id The ID of the review to update.
         * @param review The updated review content.
         * @param rating The updated rating.
         * @response A response containing the updated review and rating.
         */
        put("{id}", {
            tags("Review Rating")
            summary = "auth[customer]"
            request {
                pathParameter<String>("id") {
                    required = true
                }
                queryParameter<String>("comment") {
                    required = true
                }
                queryParameter<String>("rate") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (id, review, rating) = call.requiredParameters("id", "comment", "rate") ?: return@put
            call.respond(
                ApiResponse.success(
                    reviewRatingController.updateCommentReview(
                        id.toLong(),
                        review,
                        rating.toDouble()
                    ), HttpStatusCode.OK
                )
            )
        }

        // Route for deleting a review and rating
        /**
         * DELETE request to remove an existing review and rating.
         *
         * @param id The ID of the review to delete.
         * @response A response indicating the result of the deletion.
         */
        delete("{id}", {
            tags("Comment Review")
            summary = "auth[customer]"
            request {
                pathParameter<String>("id") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (id) = call.requiredParameters("id") ?: return@delete
            call.respond(
                ApiResponse.success(
                    reviewRatingController.deleteCommentReview(id.toLong()), HttpStatusCode.OK
                )
            )
        }
    }
}
