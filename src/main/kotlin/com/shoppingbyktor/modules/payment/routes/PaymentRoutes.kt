package com.shoppingbyktor.modules.payment.routes

import com.shoppingbyktor.modules.payment.controller.PaymentController
import com.shoppingbyktor.database.models.PaymentRequest

import com.shoppingbyktor.utils.ApiResponse
import com.shoppingbyktor.utils.extension.apiResponse
import com.shoppingbyktor.utils.extension.requiredParameters
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Defines routes for handling payments.
 *
 * Accessible by customers to create payments and retrieve payment details.
 *
 * @param paymentController The controller handling payment-related operations.
 */
fun Route.paymentRoutes(paymentController: PaymentController) {
    route("payment") {

        /**
         * POST request to create a new payment.
         *
         * Accessible by customers only.
         *
         * @param paymentRequest The payment details (e.g., amount, payment method, etc.) to process the payment.
         */
            post({
                tags("Payment")
                summary = "auth[customer]"
                request {
                    body<PaymentRequest>()
                }
                apiResponse()
            }) {
                val requestBody = call.receive<PaymentRequest>()
                call.respond(
                    ApiResponse.success(
                        paymentController.createPayment(requestBody), HttpStatusCode.OK
                    )
                )
            }

            /**
             * GET request to retrieve payment details by ID.
             *
             * Accessible by customers only.
             *
             * @param id The payment ID to retrieve.
             */
            get("{id}", {
                tags("Payment")
                summary = "auth[customer]"
                request {
                    pathParameter<String>("id") {
                        required = true
                    }
                }
                apiResponse()
            }) {
                val (id) = call.requiredParameters("id") ?: return@get
                call.respond(
                    ApiResponse.success(
                        paymentController.getPaymentById(id.toLong()), HttpStatusCode.OK
                    )
                )
            }
    }
}