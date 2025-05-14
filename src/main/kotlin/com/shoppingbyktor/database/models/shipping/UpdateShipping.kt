package com.shoppingbyktor.database.models.shipping

import com.shoppingbyktor.database.entities.ShippingTable
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate


data class UpdateShipping(
    val id: Long,
    val address: String?,
    val city: String?,
    val country: String?,
    val phone: Int?,
    val shippingMethod: String?,
    val email: String?,
    val status: ShippingTable.ShippingStatus?,
    val trackingNumber: String?
) {
    fun validation() {
        validate(this) {
            validate(UpdateShipping::id).isNotNull().isNotZero()
        }
    }
}