package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object ProductCategoryTable : BaseIntIdTable("category") {
    val name = text("name")
    val icon = text("icon").nullable()
}

class ProductCategoryDAO(id: EntityID<String>) : BaseIntEntity(id, ProductCategoryTable) {
    companion object : BaseIntEntityClass<ProductCategoryDAO>(ProductCategoryTable)

    var name by ProductCategoryTable.name
    private val subCategories by ProductSubCategoryDAO referrersOn ProductSubCategoryTable.categoryId
    var icon by ProductCategoryTable.icon
    fun response() =
        ProductCategory(id.value, name, subCategories.map { it.response() }, icon)
}

data class ProductCategory(
    val id: String,
    val name: String,
    val subCategories: List<ProductSubCategory>,
    val icon: String?
)