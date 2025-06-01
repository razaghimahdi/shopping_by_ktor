package com.shoppingbyktor.database.models.cart


import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class CartRequest(
    val product: Long,
    val count: Int
) {
    fun validation() {
        validate(this) {
            validate(CartRequest::product).isNotNull().isNotZero()
            validate(CartRequest::count).isNotNull().isGreaterThan(0)
        }
    }
}
