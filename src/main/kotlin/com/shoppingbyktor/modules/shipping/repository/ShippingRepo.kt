package com.shoppingbyktor.modules.shipping.repository

import com.shoppingbyktor.database.entities.Shipping
import com.shoppingbyktor.database.models.shipping.ShippingRequest
import com.shoppingbyktor.database.models.shipping.UpdateShipping

interface ShippingRepo {
    suspend fun createShipping(userId: Long, shippingRequest: ShippingRequest): Shipping
    suspend fun getShipping(userId: Long, orderId: Long): Shipping
    suspend fun updateShipping(userId: Long, updateShipping: UpdateShipping): Shipping
    suspend fun deleteShipping(userId: Long, id: Long):String
}