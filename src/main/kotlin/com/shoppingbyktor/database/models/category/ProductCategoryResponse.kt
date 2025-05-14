package com.shoppingbyktor.database.models.category

import com.shoppingbyktor.database.entities.ProductSubCategory


data class ProductCategoryResponse(
    val id: Long,
    val name: String,
    val subCategories: List<ProductSubCategory>,
    val icon: String?
)