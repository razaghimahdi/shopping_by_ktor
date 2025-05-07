package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object WishListTable : BaseIntIdTable("wishlist") {
    val userId = reference("user_id", UserTable.id)
    val productId = reference("product_id", ProductTable.id)
}

class WishListDAO(id: EntityID<String>) : BaseIntEntity(id, WishListTable) {
    companion object : BaseIntEntityClass<WishListDAO>(WishListTable)

    var userId by WishListTable.userId
    var productId by WishListTable.productId
    fun response(product: Product? = null) = WishList(product)
}

data class WishList(val product: Product? = null)