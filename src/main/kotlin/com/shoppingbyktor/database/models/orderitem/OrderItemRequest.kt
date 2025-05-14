package com.shoppingbyktor.database.models.orderitem

import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class OrderItemRequest(
    val productId: Long,
    val quantity: Int
) {
    fun validation() {
        validate(this) {
            validate(OrderItemRequest::productId).isNotNull().isNotZero()
            validate(OrderItemRequest::quantity).isNotNull().isGreaterThan(0)
        }
    }
}
