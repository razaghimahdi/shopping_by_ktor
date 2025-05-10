package com.shoppingbyktor.modules.auth.routes

import com.shoppingbyktor.modules.auth.controller.AuthController
import com.shoppingbyktor.database.entities.ChangePassword
import com.shoppingbyktor.database.models.user.body.ForgetPasswordRequest
import com.shoppingbyktor.database.models.user.body.JwtTokenRequest
import com.shoppingbyktor.database.models.user.body.LoginRequest
import com.shoppingbyktor.database.models.user.body.RegisterRequest
import com.shoppingbyktor.database.models.user.body.ResetRequest
import com.shoppingbyktor.utils.*
import com.shoppingbyktor.utils.extension.apiResponse
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
 * Defines authentication routes for login, registration, password reset, and password change.
 *
 * @param authController The controller handling authentication-related operations.
 */
fun Route.authRoutes(authController: AuthController) {
    route("auth") {
        /**
         * Handles the login request.
         *
         * Receives a [LoginRequest] object and responds with a successful login response.
         */
        post("login", {
            tags("Auth")
            request {
                body<LoginRequest>()
            }
            apiResponse()
        }) {
            val requestBody = call.receive<LoginRequest>()
            call.respond(
                ApiResponse.success(
                    authController.login(requestBody), HttpStatusCode.OK
                )
            )
        }

        /**
         * Handles the registration request.
         *
         * Receives a [RegisterRequest] object and responds with a successful registration response.
         */
        post("register", {
            tags("Auth")
            request {
                body<RegisterRequest>()
            }
            apiResponse()
        }) {
            val requestBody = call.receive<RegisterRequest>()
            call.respond(ApiResponse.success(authController.register(requestBody), HttpStatusCode.OK))
        }


        /**
         * Handles the request for sending a password reset verification code.
         *
         * Receives the user's email as a query parameter and sends a verification code to the email.
         */
        get("forget-password", {
            tags("Auth")
            request {
                queryParameter<String>("email") {
                    required = true
                }
                queryParameter<String>("userType") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (email, userType) = call.requiredParameters("email", "userType") ?: return@get
            val requestBody = ForgetPasswordRequest(email, userType)
            authController.forgetPassword(requestBody).let { otp ->
                sendEmail(requestBody.email, otp)
                call.respond(
                    ApiResponse.success(
                        "${AppConstants.SuccessMessage.VerificationCode.VERIFICATION_CODE_SENT_TO} ${requestBody.email}",
                        HttpStatusCode.OK
                    )
                )
            }
        }

        /**
         * Handles the request for resetting the password.
         *
         * Receives the email, OTP, and new password as query parameters and verifies the password reset code.
         */
        get("reset-password", {
            tags("Auth")
            request {
                queryParameter<String>("email") {
                    required = true
                }
                queryParameter<String>("otp") {
                    required = true
                }
                queryParameter<String>("newPassword") {
                    required = true
                }
                queryParameter<String>("userType") {
                    required = true
                }
            }
            apiResponse()
        }) {
            val (email, otp, newPassword, userType) = call.requiredParameters(
                "email", "otp", "newPassword", "userType"
            ) ?: return@get

            AuthController().resetPassword(
                ResetRequest(
                    email, otp, newPassword, userType
                )
            ).let {
                when (it) {
                    AppConstants.DataBaseTransaction.FOUND -> {
                        call.respond(
                            ApiResponse.success(
                                AppConstants.SuccessMessage.Password.PASSWORD_CHANGE_SUCCESS, HttpStatusCode.OK
                            )
                        )
                    }

                    AppConstants.DataBaseTransaction.NOT_FOUND -> {
                        call.respond(
                            ApiResponse.success(
                                AppConstants.SuccessMessage.VerificationCode.VERIFICATION_CODE_IS_NOT_VALID,
                                HttpStatusCode.OK
                            )
                        )
                    }
                }
            }
        }

        /**
         * Handles the request to change the password for authenticated users.
         *
         * Requires the old and new password as query parameters and responds with a success or failure message.
         */
             put("change-password", {
                tags("Auth")
                protected = true
                request {
                    queryParameter<String>("oldPassword") {
                        required = true
                    }
                    queryParameter<String>("newPassword") {
                        required = true
                    }
                }
                apiResponse()
            }) {
                val (oldPassword, newPassword) = call.requiredParameters("oldPassword", "newPassword") ?: return@put
                val loginUser = call.principal<JwtTokenRequest>()
                authController.changePassword(loginUser?.userId!!, ChangePassword(oldPassword, newPassword)).let {
                    if (it) call.respond(
                        ApiResponse.success(
                            "Password has been changed", HttpStatusCode.OK
                        )
                    ) else call.respond(
                        ApiResponse.failure(
                            Response.Alert("Old password is wrong"), HttpStatusCode.OK
                        )
                    )
                }
         }
    }
}