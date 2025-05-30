package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Table for storing various policy documents like privacy policy, terms, etc.
 */
object PolicyDocumentTable : BaseIntIdTable("policy_documents") {
    val title = varchar("title", 255)
    val type = enumerationByName("type", 30, PolicyType::class) // PRIVACY_POLICY, TERMS_CONDITIONS, REFUND_POLICY, etc.
    val content = text("content")
    val version = varchar("version", 50)
    val effectiveDate = varchar("effective_date", 50) // ISO date string
    val isActive = bool("is_active").default(true)

    override val primaryKey = PrimaryKey(id)

    /**
     * Enum class for policy document types
     */
    enum class PolicyType {
        PRIVACY_POLICY,
        TERMS_CONDITIONS,
        REFUND_POLICY,
        COOKIE_POLICY,
        DISCLAIMER,
        EULA,
        SHIPPING_POLICY
    }
}

/**
 * Data Access Object for policy documents
 */
class PolicyDocumentDAO(id: EntityID<Long>) : BaseIntEntity(id, PolicyDocumentTable) {
    companion object : BaseIntEntityClass<PolicyDocumentDAO>(PolicyDocumentTable)

    var title by PolicyDocumentTable.title
    var type by PolicyDocumentTable.type
    var content by PolicyDocumentTable.content
    var version by PolicyDocumentTable.version
    var effectiveDate by PolicyDocumentTable.effectiveDate
    var isActive by PolicyDocumentTable.isActive

    fun response() = PolicyDocumentResponse(
        id.value,
        title,
        type.name,
        content,
        version,
        effectiveDate,
        isActive,
    )
}

/**
 * Response model for policy documents
 */
data class PolicyDocumentResponse(
    val id: Long,
    val title: String,
    val type: String,
    val content: String,
    val version: String,
    val effectiveDate: String,
    val isActive: Boolean,
)
