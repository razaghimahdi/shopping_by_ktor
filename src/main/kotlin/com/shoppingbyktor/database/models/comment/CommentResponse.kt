package com.shoppingbyktor.database.models.comment

import com.shoppingbyktor.database.entities.CommentReview
import com.shoppingbyktor.database.entities.UserResponse

data class CommentResponse(
    val id: Long,
    val user: UserResponse,
    val comment: String,
    val created_at: String,
    val rate: Double
)

fun CommentReview.toCommentResponse(user: UserResponse) = CommentResponse(
    id = id,
    created_at = createdAt,
    comment = comment,
    rate = rate,
    user = user
)
