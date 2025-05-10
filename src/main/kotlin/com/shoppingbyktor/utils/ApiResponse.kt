package com.shoppingbyktor.utils

import io.ktor.http.*
import kotlinx.serialization.Serializable


 data class Response(
    val status: Boolean,
    val statusCode: HttpStatusCode? = null,
    val result: Any? = null,
    val alert: Alert? = null,
    ){


    @Serializable
    data class StatusCode(
        val value: Int,
        val description: String
    ){
       fun toHttpStatusCode() = HttpStatusCode(value, description)
    }

    @Serializable
    data class Alert(
        val message: String?,
        val title: String? = null,
    )

}


object ApiResponse {
    fun <T> success(data: T, statsCode: HttpStatusCode?, alert: Response.Alert? = null) =
        Response(true, result = data, alert = alert, statusCode = statsCode)

    fun failure(alert: Response.Alert, statsCode: HttpStatusCode?) =
        Response(false, alert = alert, statusCode = statsCode)

    fun HttpStatusCode.toStatusCode() = Response.StatusCode(value, description)
}
