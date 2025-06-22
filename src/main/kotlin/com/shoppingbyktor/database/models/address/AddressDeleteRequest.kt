package com.shoppingbyktor.database.models.address


import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class AddressDeleteRequest(
    val addressId: Long,
) {
    fun validation() {
        validate(this) {
            validate(AddressDeleteRequest::addressId).isNotNull().isNotZero()
        }
    }
}
