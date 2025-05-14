package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object BannerTable : BaseIntIdTable("banner") {
     val banner = text("banner")
}

class BannerDAO(id: EntityID<Long>) : BaseIntEntity(id, BannerTable) {
    companion object : BaseIntEntityClass<BannerDAO>(BannerTable)

    var banner by BannerTable.banner
    fun response() = Banner(
        id.value,
        banner
    )
}

data class Banner(
    val id: Long,
    val banner: String
)