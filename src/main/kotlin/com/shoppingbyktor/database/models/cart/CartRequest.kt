package com.shoppingbyktor.database.models.cart


import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class CartRequest(
    val productId: Long,
    val quantity: Int
) {
    fun validation() {
        validate(this) {
            validate(CartRequest::productId).isNotNull().isNotZero()
            validate(CartRequest::quantity).isNotNull().isGreaterThan(0)
        }
    }
}
