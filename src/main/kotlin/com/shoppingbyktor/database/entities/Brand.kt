package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable

import org.jetbrains.exposed.dao.id.EntityID

object BrandTable : BaseIntIdTable("brand") {
    val name = text("name")
    val logo = text("logo").nullable()
}

class BrandDAO(id: EntityID<Long>) : BaseIntEntity(id, BrandTable) {
    companion object : BaseIntEntityClass<BrandDAO>(BrandTable)

    var name by BrandTable.name
    var logo by BrandTable.logo
    fun response() = Brand(id.value, name, logo)
}

data class Brand(val id: Long, val name: String, val logo: String?)