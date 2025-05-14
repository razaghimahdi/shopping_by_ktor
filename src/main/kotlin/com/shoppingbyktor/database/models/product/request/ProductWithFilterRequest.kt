package com.shoppingbyktor.database.models.product.request

import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class ProductWithFilterRequest(
    val limit: Int,
    val maxPrice: Long?,
    val minPrice: Long?,
    val categoryId: Long?,
    val subCategoryId: Long?,
    val brandId: Long?,
) {
    fun validation() {
        validate(this) {
            validate(ProductWithFilterRequest::limit).isNotNull().isNotZero()
        }
    }
}
