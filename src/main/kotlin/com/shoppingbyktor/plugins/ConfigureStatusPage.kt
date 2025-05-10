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
                    val errorMessage =
                        error.constraintViolations.mapToMessage(baseName = "messages", locale = Locale.ENGLISH)
                            .map { "${it.property}: ${it.message}" }.toString()
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(
                            Response.Alert(errorMessage), HttpStatusCode.BadRequest
                        )
                    )
                }

                is MissingRequestParameterException -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(
                            Response.Alert(error.message), HttpStatusCode.BadRequest
                        )
                    )
                }

                is EmailNotExist -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(Response.Alert("User not exist"), HttpStatusCode.BadRequest)
                    )
                }

                is NullPointerException -> {
                    call.respond(
                        ApiResponse.failure(
                            Response.Alert("Null pointer error : ${error.message}"), HttpStatusCode.BadRequest
                        )
                    )
                }

                is UserNotExistException -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(Response.Alert("User not exist"), HttpStatusCode.BadRequest)
                    )
                }

                is FillInputCorrect -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(Response.Alert("Fill inputs correct!","Warning"), HttpStatusCode.BadRequest)
                    )
                }

                is PasswordNotMatch -> {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ApiResponse.failure(Response.Alert(title = "Failed", message = "Password is wrong"), HttpStatusCode.BadRequest)
                    )
                }

                is TypeCastException -> {
                    call.respond(
                        ApiResponse.failure(Response.Alert("Type cast exception"), HttpStatusCode.BadRequest)
                    )
                }

                is CommonException -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ApiResponse.failure(Response.Alert(error.message), HttpStatusCode.BadRequest)
                    )
                }

                else -> {
                    call.respond(
                        HttpStatusCode.InternalServerError, ApiResponse.failure(
                            Response.Alert("Internal server error : ${error.message}"), HttpStatusCode.InternalServerError
                        )
                    )
                }
            }
        }
        status(HttpStatusCode.Unauthorized) { call, statusCode ->
            call.respond(HttpStatusCode.Unauthorized, ApiResponse.failure(Response.Alert("Unauthorized api call"), statusCode))
        }
    }
}