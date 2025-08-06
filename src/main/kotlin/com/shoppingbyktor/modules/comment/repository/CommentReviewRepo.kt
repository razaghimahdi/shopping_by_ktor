package com.shoppingbyktor.modules.comment.repository

import com.shoppingbyktor.database.entities.CommentReview
import com.shoppingbyktor.database.models.comment.CommentResponse
import com.shoppingbyktor.database.models.comment.CommentReviewRequest

interface CommentReviewRepo {
    suspend fun getCommentReview(productId: Long): List<CommentResponse>
    suspend fun addCommentReview(userId: Long, commentReview: CommentReviewRequest): Any?
    suspend fun updateCommentReview(reviewId: Long, comment: String, rate: Double): Any?
    suspend fun deleteCommentReview(reviewId: Long): Any?
}