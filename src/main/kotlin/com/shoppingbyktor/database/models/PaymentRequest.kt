package com.shoppingbyktor.database.models

import com.shoppingbyktor.database.entities.PaymentTable
import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class PaymentRequest(
    val orderId: Long,
    val amount: Long,
    val status: PaymentTable.PaymentStatus,
    val paymentMethod: String,
    val transactionId: String?,
) {
    fun validation() {
        validate(this) {
            validate(PaymentRequest::orderId).isNotNull().isNotZero()
            validate(PaymentRequest::amount).isNotNull().isGreaterThan(0)
            validate(PaymentRequest::status).isNotNull()
            validate(PaymentRequest::paymentMethod).isNotNull()
        }
    }
}
