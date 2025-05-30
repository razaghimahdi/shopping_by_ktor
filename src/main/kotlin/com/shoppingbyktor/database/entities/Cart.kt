package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object CartItemTable : BaseIntIdTable("cart_item") {
    val userId = reference("user_id", UserTable.id)
    val productId = reference("product_id", ProductTable.id)
    val quantity = integer("quantity").default(1)
}

class CartItemDAO(id: EntityID<Long>) : BaseIntEntity(id, CartItemTable) {
    companion object : BaseIntEntityClass<CartItemDAO>(CartItemTable)

    var userId by CartItemTable.userId
    var productId by CartItemTable.productId
    var quantity by CartItemTable.quantity
    fun response(product: Product? = null) = Cart(productId.value, quantity, product)
}

data class Cart(
    val productId: Long,
    val quantity: Int,
    val product: Product?
)