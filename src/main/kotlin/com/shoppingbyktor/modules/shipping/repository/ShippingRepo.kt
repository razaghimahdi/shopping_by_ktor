package com.shoppingbyktor.modules.shipping.repository

import com.shoppingbyktor.database.entities.Shipping
import com.shoppingbyktor.database.models.shipping.ShippingRequest
import com.shoppingbyktor.database.models.shipping.UpdateShipping

interface ShippingRepo {
    suspend fun createShipping(userId: String, shippingRequest: ShippingRequest): Shipping
    suspend fun getShipping(userId: String, orderId: String): Shipping
    suspend fun updateShipping(userId: String, updateShipping: UpdateShipping): Shipping
    suspend fun deleteShipping(userId: String, id: String):String
}