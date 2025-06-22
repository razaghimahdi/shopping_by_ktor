package com.shoppingbyktor.database.models.address


import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class AddressRequest(
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
            validate(AddressRequest::address).isNotNull().isNotEmpty()
            validate(AddressRequest::country).isNotNull().isNotEmpty()
            validate(AddressRequest::city).isNotNull().isNotEmpty()
            validate(AddressRequest::state).isNotNull().isNotEmpty()
            validate(AddressRequest::zipCode).isNotNull().isNotEmpty()
        }
    }
}
