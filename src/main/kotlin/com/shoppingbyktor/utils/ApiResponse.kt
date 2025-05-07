package com.shoppingbyktor.utils

import io.ktor.http.*

data class Response(
    val status: Boolean,
    val statusCode: HttpStatusCode? = null,
    val result: Any? = null,
    val alert: AlertResponse? = null,
    )


data class AlertResponse(
    val message: String?,
    val title: String? = null,
)

object ApiResponse {
    fun <T> success(data: T, statsCode: HttpStatusCode?, alert: AlertResponse? = null) =
        Response(true, result = data, alert = alert, statusCode = statsCode)

    fun failure(alert: AlertResponse, statsCode: HttpStatusCode?) =
        Response(false, alert = alert, statusCode = statsCode)
}
