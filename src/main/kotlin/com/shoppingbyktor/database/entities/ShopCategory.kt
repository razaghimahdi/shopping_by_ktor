package com.shoppingbyktor.database.entities


import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object ShopCategoryTable : BaseIntIdTable("shop_category") {
    val name = text("name")
}

class ShopCategoryDAO(id: EntityID<Long>) : BaseIntEntity(id, ShopCategoryTable) {
    companion object : BaseIntEntityClass<ShopCategoryDAO>(ShopCategoryTable)

    var name by ShopCategoryTable.name
    fun response() = ShopCategory(id.value, name)
}

data class ShopCategory(val id: Long, val name: String)
