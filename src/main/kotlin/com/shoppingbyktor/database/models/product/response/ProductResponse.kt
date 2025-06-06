package com.shoppingbyktor.database.models.product.response

data class ProductResponse(
    val categoryId: Long,
    val title: String,
    val productImage:List<String>,
    val image:String,
    val description: String,
    val color: String?,
    val size: String?,
    val price: Long,
    val discountPrice: Double?,
    val quantity: Int,
    val likes: Int,
    val isLike:Boolean,
    val videoLink: String,
)