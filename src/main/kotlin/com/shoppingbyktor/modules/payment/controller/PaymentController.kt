package com.shoppingbyktor.modules.payment.controller

import com.shoppingbyktor.database.entities.OrderDAO
import com.shoppingbyktor.database.entities.OrderTable
import com.shoppingbyktor.database.entities.Payment
import com.shoppingbyktor.database.entities.PaymentDAO
import com.shoppingbyktor.database.entities.PaymentTable
import com.shoppingbyktor.database.models.PaymentRequest
import com.shoppingbyktor.modules.payment.repository.PaymentRepo
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Controller for managing payment-related operations.
 */
class PaymentController : PaymentRepo {

    /**
     * Creates a new payment for an order based on the provided payment details.
     *
     * @param paymentRequest The details of the payment including order ID, amount, status, and payment method.
     * @return The created payment entity.
     * @throws Exception if the order with the provided order ID is not found.
     */
    override suspend fun createPayment(paymentRequest: PaymentRequest): Payment = query {
        val isOrderExist = OrderDAO.Companion.find { OrderTable.id eq paymentRequest.orderId }.toList().singleOrNull()
        isOrderExist?.let {
            PaymentDAO.Companion.new {
                orderId = EntityID(paymentRequest.orderId, PaymentTable)
                amount = paymentRequest.amount
                status = paymentRequest.status
                paymentMethod = paymentRequest.paymentMethod
                transactionId = paymentRequest.transactionId
            }.response()
        } ?: throw paymentRequest.orderId.notFoundException()
    }

    /**
     * Retrieves a payment by its ID.
     *
     * @param paymentId The ID of the payment to retrieve.
     * @return The payment entity associated with the provided payment ID.
     * @throws Exception if no payment is found for the given payment ID.
     */
    override suspend fun getPaymentById(paymentId: Long): Payment = query {
        val isOrderExist = PaymentDAO.Companion.find { PaymentTable.id eq paymentId }.toList().firstOrNull()
        isOrderExist?.response() ?: throw paymentId.notFoundException()
    }
}