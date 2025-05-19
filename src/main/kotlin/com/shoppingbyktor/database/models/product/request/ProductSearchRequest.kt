package com.shoppingbyktor.database.models.product.request

import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isNotZero
import org.valiktor.validate

data class ProductSearchRequest(
    val title: String?,
    val minPrice: Long?,
    val maxPrice: Long?,
    val categoriesId: String?,
    val page: Long?,
    val sort: String?
) {
    fun validation() {
       // validate(this) {
           // validate(ProductSearchRequest::page).isNotNull().isNotZero() FIXME()
           // validate(ProductSearchRequest::title).isNotNull().isNotEmpty() FIXME()
        //}
    }
}
