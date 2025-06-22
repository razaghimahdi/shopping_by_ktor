package com.shoppingbyktor.database.entities


import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object UserProfileTable : BaseIntIdTable("user_profile") {
    val userId = reference("user_id", UserTable.id)
    val image = text("image").default("")
    val name = text("name").default("")
    val email = text("email").default("")
    val age = integer("age").default(0)
}

class UsersProfileDAO(id: EntityID<Long>) : BaseIntEntity(id, UserProfileTable) {
    companion object : BaseIntEntityClass<UsersProfileDAO>(UserProfileTable)

    var userId by UserProfileTable.userId
    var image by UserProfileTable.image
    var age by UserProfileTable.age
    var name by UserProfileTable.name
    var email by UserProfileTable.email
    fun response() = UserProfile(
        userId.value,
        image,
        age,
        name,
        email
    )
}

data class UserProfile(
    var userId: Long,
    val profile_url: String,
    val age: Int,
    val name: String,
    val email: String,
)

