package com.shoppingbyktor.database.models.product.request

data class UpdateProduct(
    val categoryId: Long?,
    val subCategoryId: Long?,
    val brandId: Long?,
    val title: String?,
    val description: String?,
    val stockQuantity: Int?,
    val price: Long?,
    val discountPrice: Double?,
    val status: String?,
    val videoLink: String?,
    val hotDeal: Boolean?,
    val featured: Boolean?,
    val gallery: List<String>,
)
