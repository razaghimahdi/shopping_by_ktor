package com.shoppingbyktor.database.models.shipping

import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate


data class ShippingRequest(
    val orderId: Long,
    val address: String,
    val city: String,
    val country: String,
    val phone: Int,
    val email: String?,
    val shippingMethod: String?,
) {
    fun validation() {
        validate(this) {
            validate(ShippingRequest::orderId).isNotNull().isNotZero()
            validate(ShippingRequest::address).isNotNull().isNotEmpty()
            validate(ShippingRequest::email).isEmail()
        }
    }
}