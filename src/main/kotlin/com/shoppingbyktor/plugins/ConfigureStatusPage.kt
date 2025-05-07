package com.shoppingbyktor.plugins

import com.shoppingbyktor.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage
import java.util.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<Throwable> { call, error ->
            when (error) {
                is ConstraintViolationException -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(
                            AlertResponse(error.message), HttpStatusCode.BadRequest
                        )
                    )
                }

                is MissingRequestParameterException -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(
                            AlertResponse(error.message), HttpStatusCode.BadRequest
                        )
                    )
                }

                is EmailNotExist -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(AlertResponse("User not exist"), HttpStatusCode.BadRequest)
                    )
                }

                is NullPointerException -> {
                    call.respond(
                        ApiResponse.failure(
                            AlertResponse("Null pointer error : ${error.message}"), HttpStatusCode.BadRequest
                        )
                    )
                }

                is UserNotExistException -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(AlertResponse("User not exist"), HttpStatusCode.BadRequest)
                    )
                }

                is PasswordNotMatch -> {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse.failure(AlertResponse("Password is wrong"), HttpStatusCode.BadRequest)
                    )
                }

                is TypeCastException -> {
                    call.respond(
                        ApiResponse.failure(AlertResponse("Type cast exception"), HttpStatusCode.BadRequest)
                    )
                }

                is CommonException -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(AlertResponse(error.message), HttpStatusCode.BadRequest)
                    )
                }

                else -> {
                    call.respond(
                        HttpStatusCode.InternalServerError, ApiResponse.failure(
                            AlertResponse("Internal server error : ${error.message}"), HttpStatusCode.InternalServerError
                        )
                    )
                }
            }
        }
        status(HttpStatusCode.Unauthorized) { call, statusCode ->
            call.respond(HttpStatusCode.Unauthorized, ApiResponse.failure(AlertResponse("Unauthorized api call"), statusCode))
        }
    }
}