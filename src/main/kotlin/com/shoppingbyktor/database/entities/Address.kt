package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object AddressTable : BaseIntIdTable("address") {
    val userId = reference("user_id", UserTable.id)
    val address = text("address").default("")
    val country = text("country").default("")
    val city = text("city").default("")
    val state = text("state").default("")
    val zipCode = text("zipCode").default("")
    val lan = text("lan").default("")
    val lat = text("lat").default("")
}

class AddressDAO(id: EntityID<Long>) : BaseIntEntity(id, AddressTable) {
    companion object : BaseIntEntityClass<AddressDAO>(AddressTable)

    var userId by AddressTable.userId
    var address by AddressTable.address
    var country by AddressTable.country
    var city by AddressTable.city
    var state by AddressTable.state
    var zipCode by AddressTable.zipCode
    var lan by AddressTable.lan
    var lat by AddressTable.lat
    fun response() = Address(address, country, city, state, zipCode, lan, lat)
}

data class Address(
    val address: String,
    val country: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val lan: String,
    val lat: String,
)