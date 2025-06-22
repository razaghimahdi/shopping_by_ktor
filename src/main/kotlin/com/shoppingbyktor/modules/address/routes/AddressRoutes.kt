package com.shoppingbyktor.modules.address.routes

import com.shoppingbyktor.database.models.address.AddressDeleteRequest
import com.shoppingbyktor.database.models.address.AddressRequest
import com.shoppingbyktor.database.models.address.UpdateAddressRequest
import com.shoppingbyktor.database.models.cart.CartDeleteRequest
import com.shoppingbyktor.modules.cart.controller.CartController
import com.shoppingbyktor.database.models.cart.CartRequest
import com.shoppingbyktor.modules.address.controller.AddressController

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
 * Defines routes for managing the user addresses.
 *
 * Accessible by customers only. It includes operations to create, get, update and remove addresses.
 *
 * @param cartController The controller handling address-related operations.
 */
fun Route.addressRoutes(addressController: AddressController) {
    route("address") {
        /**
         * POST request to add an address.
         *
         * Accessible by customers only.
         *
         * @param AddressRequest bunch of parameters to add address.
         */
        authenticate("jwt") {
            post({
                tags("Cart")
                summary = "auth[customer]"
                request {
                    body<AddressRequest>()
                }
                apiResponse()
            }) {
                val requestBody = call.receive<AddressRequest>()
                call.respond(
                    ApiResponse.success(
                        addressController.createAddress(
                            call.currentUser().userId,
                            requestBody.address,
                            requestBody.country,
                            requestBody.city,
                            requestBody.state,
                            requestBody.zipCode,
                            requestBody.lan,
                            requestBody.lat
                        ), HttpStatusCode.OK
                    )
                )
            }
        }

        /**
         * GET request to retrieve items from the address with a specified limit.
         *
         * Accessible by customers only.
         *
         */
        authenticate("jwt") {
            get({
                tags("Cart")
                summary = "auth[customer]"
                apiResponse()
            }) {
                call.respond(
                    ApiResponse.success(
                        addressController.getAddresses(
                            call.currentUser().userId
                        ), HttpStatusCode.OK
                    )
                )
            }
        }


        /**
         * DELETE request to remove a specific address .
         *
         * Accessible by customers only.
         *
         * @param addressId The ID of the address to remove from the addresses.
         */
        authenticate("jwt") {
            post("delete", {
                tags("Address")
                summary = "auth[customer]"
                request {
                    body<AddressDeleteRequest>()
                }
                apiResponse()
            }) {
                val requestBody = call.receive<AddressDeleteRequest>()
                call.respond(
                    ApiResponse.success(
                        addressController.removeAddress(call.currentUser().userId, requestBody.addressId),
                        HttpStatusCode.OK
                    )
                )
            }
        }

        /**
         * POST request to update an address.
         *
         * Accessible by customers only.
         *
         * @param UpdateAddressRequest bunch of parameters to update exist address.
         */
        authenticate("jwt") {
            post({
                tags("Cart")
                summary = "auth[customer]"
                request {
                    body<UpdateAddressRequest>()
                }
                apiResponse()
            }) {
                val requestBody = call.receive<UpdateAddressRequest>()
                call.respond(
                    ApiResponse.success(
                        addressController.updateAddress(
                            call.currentUser().userId,
                            requestBody.addressId,
                            requestBody.address,
                            requestBody.country,
                            requestBody.city,
                            requestBody.state,
                            requestBody.zipCode,
                            requestBody.lan,
                            requestBody.lat
                        ), HttpStatusCode.OK
                    )
                )
            }
        }
    }
}