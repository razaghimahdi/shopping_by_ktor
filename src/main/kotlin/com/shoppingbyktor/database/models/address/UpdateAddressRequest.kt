package com.shoppingbyktor.database.models.address


import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class UpdateAddressRequest(
    val addressId:Long,
    val address: String,
    val country: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val lan: String,
    val lat: String
) {
    fun validation() {
        validate(this) {
            validate(UpdateAddressRequest::addressId).isNotNull().isNotZero()
            validate(UpdateAddressRequest::address).isNotNull().isNotEmpty()
            validate(UpdateAddressRequest::country).isNotNull().isNotEmpty()
            validate(UpdateAddressRequest::city).isNotNull().isNotEmpty()
            validate(UpdateAddressRequest::state).isNotNull().isNotEmpty()
            validate(UpdateAddressRequest::zipCode).isNotNull().isNotEmpty()
        }
    }
}
