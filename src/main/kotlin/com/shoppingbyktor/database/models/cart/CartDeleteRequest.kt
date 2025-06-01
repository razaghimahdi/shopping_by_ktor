package com.shoppingbyktor.database.models.cart


import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class CartDeleteRequest(
    val product: Long,
) {
    fun validation() {
        validate(this) {
            validate(CartDeleteRequest::product).isNotNull().isNotZero()
        }
    }
}
