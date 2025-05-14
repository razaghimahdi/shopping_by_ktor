package com.shoppingbyktor.database.models

import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class WisListRequest(val productId: Long) {
    fun validation() {
        validate(this) {
            validate(WisListRequest::productId).isNotNull().isNotZero()
        }
    }
}
