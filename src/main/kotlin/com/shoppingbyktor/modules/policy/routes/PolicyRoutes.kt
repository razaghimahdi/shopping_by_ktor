package com.shoppingbyktor.modules.policy.routes

import com.shoppingbyktor.modules.policy.controller.PolicyController
import com.shoppingbyktor.database.entities.PolicyDocumentTable
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
 * Defines routes for managing policy documents including privacy policy, terms and conditions, refund policy, etc.
 * Different routes are available based on user roles (CUSTOMER, ADMIN).
 *
 * @param policyController The controller handling policy-related operations.
 */
fun Route.policyRoutes(policyController: PolicyController) {
    // Main route for policy management
    route("policy") {
        // Public routes for accessing policies - no authentication required

        /**
         * GET request to retrieve all policies, optionally filtered by type.
         *
         * Accessible by anyone.
         *
         * @param type Optional filter by policy type (PRIVACY_POLICY, TERMS_CONDITIONS, etc.)
         */
        get({
            tags("Privacy Policy")
            request {
                queryParameter<String>("type") {
                    description = "Filter policies by type"
                    required = false
                }
            }
            apiResponse()
        }) {
            val type = call.request.queryParameters["type"]
            val policyType = type?.let {
                PolicyDocumentTable.PolicyType.valueOf(type)
            }
            call.respond(
                ApiResponse.success(
                    policyController.getAllPolicies(policyType),
                    HttpStatusCode.OK
                )
            )
        }

        /**
         * GET request to retrieve the active policy of a specific type.
         *
         * Accessible by anyone.
         *
         * @param type The policy type (PRIVACY_POLICY, TERMS_CONDITIONS, etc.)
         */
        get("{type}", {
            tags("Privacy Policy")
            request {
                pathParameter<String>("type") {
                    description = "Policy type like PRIVACY_POLICY, TERMS_CONDITIONS, etc."
                    required = true
                }
            }
            apiResponse()
        }) {
            val (type) = call.requiredParameters("type") ?: return@get
            call.respond(
                ApiResponse.success(
                    policyController.getPolicyByType(
                        PolicyDocumentTable.PolicyType.valueOf(
                            type
                        )
                    ), HttpStatusCode.OK
                )
            )
        }

        /**
         * GET request to retrieve a specific policy by ID.
         *
         * Accessible by anyone.
         *
         * @param id The unique identifier of the policy.
         */
        get("detail/{id}", {
            tags("Privacy Policy")
            request {
                pathParameter<String>("id") {
                    description = "Policy ID"
                    required = true
                }
            }
            apiResponse()
        }) {
            val (id) = call.requiredParameters("id") ?: return@get
            call.respond(ApiResponse.success(policyController.getPolicyById(id), HttpStatusCode.OK))
        }

    }
}