package com.shoppingbyktor.database.models.home.response

import com.shoppingbyktor.database.entities.Banner
import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.entities.ProductCategory


data class HomeResponse(
    val banners: List<Banner>,
    val categories: List<ProductCategory>,
    val newest_product: List<Product>,
    val flash_sale: FlashSaleResponse,
    val most_sale: List<Product>,
)
