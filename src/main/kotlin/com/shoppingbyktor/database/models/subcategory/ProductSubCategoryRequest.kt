package com.shoppingbyktor.database.models.subcategory

import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class ProductSubCategoryRequest(
    val categoryId: Long,
    val name: String
) {
    fun validation() {
        validate(this) {
            validate(ProductSubCategoryRequest::categoryId).isNotNull().isNotZero()
            validate(ProductSubCategoryRequest::name).isNotNull().isNotEmpty()
        }
    }
}