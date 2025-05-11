package com.shoppingbyktor.database.models.home.response

import com.shoppingbyktor.database.entities.Product

data class FlashSaleResponse(
    val products: List<Product>,
    val expired_at: String?,
)
