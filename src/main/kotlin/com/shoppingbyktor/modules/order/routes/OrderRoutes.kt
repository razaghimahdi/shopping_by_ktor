package com.shoppingbyktor.modules.order.routes


import com.shoppingbyktor.modules.order.controller.OrderController
import com.shoppingbyktor.database.entities.OrderTable
import com.shoppingbyktor.database.models.order.OrderRequest

import com.shoppingbyktor.utils.ApiResponse
import com.shoppingbyktor.utils.extension.apiResponse
import com.shoppingbyktor.utils.extension.currentUser
import com.shoppingbyktor.utils.extension.requiredParameters
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.put
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Defines routes for managing orders.
 *
 * Accessible by customers and sellers with different roles and actions allowed for each.
 *
 * @param orderController The controller handling order-related operations.
 */
fun Route.orderRoutes(orderController: OrderController) {
    route("/order") {

        /**
         * POST request to create a new order.
         *
         * Accessible by customers only.
         *
         * @param orderRequest The order details (product ID, quantity, etc.) to create the order.
         */
        post({
            tags("Order")
            summary = "auth[customer]"
            request {
                body<OrderRequest>()
            }
            apiResponse()
        }) {
            val requestBody = call.receive<OrderRequest>()
            call.respond(
                ApiResponse.success(
                    orderController.createOrder(call.currentUser().userId, requestBody), HttpStatusCode.OK
                )
            )
        }

        /**
         * GET request to retrieve orders placed by the customer.
         *
         * Accessible by customers only.
         *
         * @param limit The maximum number of orders to retrieve.
         */
        get({
            tags("Order")
            summary = "auth[customer]"
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
                    orderController.getOrders(
                        call.currentUser().userId, limit.toInt()
                    ), HttpStatusCode.OK
                )
            )
        }

        /**
         * PUT request to cancel an order.
         *
         * Accessible by customers only.
         *
         * @param id The order ID to cancel.
         */
        put("{id}/cancel", {
            tags("Order")
            summary = "auth[customer]"
            request {
                pathParameter<String>("id") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (id) = call.requiredParameters("id") ?: return@put
            call.respond(
                ApiResponse.success(
                    orderController.updateOrderStatus(call.currentUser().userId, id.toLong(), OrderTable.OrderStatus.CANCELED),
                    HttpStatusCode.OK
                )
            )
        }

        /**
         * PUT request to mark an order as received.
         *
         * Accessible by customers only.
         *
         * @param id The order ID to mark as received.
         */
        put("{id}/receive", {
            tags("Order")
            summary = "auth[customer]"
            request {
                pathParameter<String>("id") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (id) = call.requiredParameters("id") ?: return@put
            call.respond(
                ApiResponse.success(
                    orderController.updateOrderStatus(call.currentUser().userId, id.toLong(), OrderTable.OrderStatus.RECEIVED),
                    HttpStatusCode.OK
                )
            )
        }

    }
}