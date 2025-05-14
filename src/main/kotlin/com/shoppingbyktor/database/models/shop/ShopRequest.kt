package com.shoppingbyktor.database.models.shop

import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class ShopRequest(
    val name: String,
    val categoryId: Long
) {
    fun validation() {
        validate(this) {
            validate(ShopRequest::name).isNotNull().isNotEmpty()
            validate(ShopRequest::categoryId).isNotNull().isNotZero()
        }
    }
}
