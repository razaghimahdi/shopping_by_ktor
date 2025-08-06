package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object CommentReviewTable : BaseIntIdTable("comment_review") {
    val userId = reference("user_id", UserTable.id)
    val productId = reference("product_id", ProductTable.id)
    val comment = varchar("comment", 500)
    val rate = double("rate").check { it.between(1.0, 5.0) }
}

class CommentReviewDAO(id: EntityID<Long>) : BaseIntEntity(id, CommentReviewTable) {
    companion object : BaseIntEntityClass<CommentReviewDAO>(CommentReviewTable)

    var userId by CommentReviewTable.userId
    var productId by CommentReviewTable.productId
    var comment by CommentReviewTable.comment
    var rate by CommentReviewTable.rate

    fun response() = CommentReview(
        id = id.value,
        userId = userId.value,
        productId = productId.value,
        comment = comment,
        rate = rate,
        createdAt = createdAt.toString(),
    )
}

data class CommentReview(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val comment: String,
    val createdAt: String,
    val rate: Double
)