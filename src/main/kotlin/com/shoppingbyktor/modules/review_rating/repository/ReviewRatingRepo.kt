package com.shoppingbyktor.modules.review_rating.repository

import com.shoppingbyktor.database.entities.ReviewRating
import com.shoppingbyktor.database.models.ReviewRatingRequest

interface ReviewRatingRepo {
    suspend fun getReviewRating(productId: Long, limit: Int): List<ReviewRating>
    suspend fun addReviewRating(userId: Long, reviewRating: ReviewRatingRequest): ReviewRating
    suspend fun updateReviewRating(reviewId: Long, review: String, rating: Int): ReviewRating
    suspend fun deleteReviewRating(reviewId: Long): String
}