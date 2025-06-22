package com.shoppingbyktor.modules.address.controller

import com.shoppingbyktor.database.entities.*
import com.shoppingbyktor.modules.address.repository.AddressRepo
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and

class AddressController : AddressRepo {

    override suspend fun createAddress(
        userId: Long,
        address: String,
        country: String,
        city: String,
        state: String,
        zipCode: String,
        lan: String,
        lat: String
    ): Any? = query {

        AddressDAO.Companion.new {
            this.userId = EntityID(userId, AddressTable)
            this.address = address
            this.country = country
            this.city = city
            this.state = state
            this.zipCode = zipCode
            this.lan = lan
            this.lat = lat
        }

        return@query null
    }

    override suspend fun getAddresses(userId: Long): List<Address> = query {
        AddressDAO.Companion.find { AddressTable.userId eq userId }.map {
            it.response()
        }
    }

    override suspend fun updateAddress(
        userId: Long,
        addressId: Long,
        address: String,
        country: String,
        city: String,
        state: String,
        zipCode: String,
        lan: String,
        lat: String
    ): Address = query {
        val isAddressExist =
            AddressDAO.Companion.find { AddressTable.userId eq userId and (AddressTable.id eq addressId) }
                .toList().singleOrNull()
        isAddressExist?.let {
            it.address = address
            it.country = country
            it.city = city
            it.state = state
            it.zipCode = zipCode
            it.lan = lan
            it.lat = lat
            it.response()
        } ?: throw addressId.notFoundException()
    }

    override suspend fun removeAddress(
        userId: Long,
        addressId: Long,
    ): Any? = query {
        val isAddressExist = AddressDAO.Companion.find { AddressTable.userId eq userId }.toList()
        if (isAddressExist.isEmpty()) {
            true
        } else {
            isAddressExist.forEach {
                it.delete()
            }
            true
        }
    }
}