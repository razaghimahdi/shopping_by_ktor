package com.shoppingbyktor.database.models.search

import com.shoppingbyktor.database.entities.ProductCategory

data class FilterResponse(
    val min_price:Long,
    val max_price:Long,
    val categories: List<ProductCategory>,
)
