package com.shoppingbyktor.database.entities


import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object ProductSubCategoryTable : BaseIntIdTable("sub_category") {
    val categoryId = reference("category_id", ProductCategoryTable.id)
    val name = text("name")
    val image = text("image").nullable()
}

class ProductSubCategoryDAO(id: EntityID<Long>) : BaseIntEntity(id, ProductSubCategoryTable) {
    companion object : BaseIntEntityClass<ProductSubCategoryDAO>(ProductSubCategoryTable)

    var categoryId by ProductSubCategoryTable.categoryId
    var name by ProductSubCategoryTable.name
    var image by ProductSubCategoryTable.image
    fun response() = ProductSubCategory(id.value, categoryId.value, name, image)
}

data class ProductSubCategory(val id: Long, val categoryId: Long, val name: String, val image: String?)