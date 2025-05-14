package com.shoppingbyktor.database.entities.base

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

abstract class BaseIntIdTable(name: String, schema: String? = "shopdev") : IdTable<Long>("$schema.$name") {
    override val id = long("id").clientDefault { generateId() }.autoIncrement().entityId()

   // override val tableName: String = "$schema.$name"

    val createdAt = datetime("created_at").clientDefault { currentUtc() }
    val updatedAt = datetime("updated_at").nullable()
    override val primaryKey = PrimaryKey(id)
}

abstract class BaseIntEntity(id: EntityID<Long>, table: BaseIntIdTable) : Entity<Long>(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
}
abstract class BaseIntEntityClass<E : BaseIntEntity>(table: BaseIntIdTable) : EntityClass<Long, E>(table){
    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                try {
                    action.toEntity(this)?.updatedAt = currentUtc()
                } catch (e: Exception) {
                    //nothing much to do here
                }
            }
        }
    }
}

private fun generateId(): Long {
    val timestamp = System.currentTimeMillis()
    val randomPart = (Math.random() * 1000).toLong()
    return timestamp * 1000 + randomPart
}

// generating utc time
private fun currentUtc(): LocalDateTime =  LocalDateTime.now(ZoneOffset.UTC)

