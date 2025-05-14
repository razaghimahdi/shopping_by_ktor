package com.shoppingbyktor.database.entities

import com.shoppingbyktor.modules.auth.controller.JwtController
import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import com.shoppingbyktor.database.models.user.body.JwtTokenRequest
import org.jetbrains.exposed.dao.id.EntityID

object UserTable : BaseIntIdTable("user") {
    val email = varchar("email", 255)
    val name = varchar("name", 100)
    val password = varchar("password", 200)
    override val primaryKey = PrimaryKey(id)

    // Create a composite unique index on email and userType
    init {
        uniqueIndex("email_userType_idx", email, name)
    }
}

class UserDAO(id: EntityID<Long>) : BaseIntEntity(id, UserTable) {
    companion object : BaseIntEntityClass<UserDAO>(UserTable)

    var email by UserTable.email
    var name by UserTable.name
    var password by UserTable.password


    fun loggedInWithToken() = LoginResponse(
       JwtController.tokenProvider(JwtTokenRequest(id.value, email))
    )
}

data class UserResponse(
    val id: Long,
    val email: String,
    var name: String
)

data class LoginResponse( val accessToken: String)
data class ChangePassword(val oldPassword: String, val newPassword: String)