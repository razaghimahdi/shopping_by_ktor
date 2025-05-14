package com.shoppingbyktor.database.models.product.request

import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class ProductRequest(
    val categoryId: Long,
    val subCategoryId: Long?,
    val brandId: Long?,
    val title: String,
    val description: String,
    val productCode: String?,
    val stockQuantity: Int,
    val price: Long,
    val discountPrice: Double?,
    val status: Int?,
    val videoLink: String?,
    val hotDeal: Boolean,
    val featured: Boolean,
    val gallery: List<String>,
) {
    fun validation() {
        validate(this) {
            validate(ProductRequest::categoryId).isNotNull().isNotZero()
            validate(ProductRequest::title).isNotNull().isNotEmpty()
            validate(ProductRequest::description).isNotNull().isNotEmpty()
            validate(ProductRequest::price).isNotNull().isGreaterThan(0L)
            validate(ProductRequest::stockQuantity).isNotNull().isGreaterThan(0)
        }
    }
}
