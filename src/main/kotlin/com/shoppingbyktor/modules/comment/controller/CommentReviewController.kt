package com.shoppingbyktor.modules.comment.controller

import com.shoppingbyktor.database.entities.*
import com.shoppingbyktor.database.models.comment.CommentResponse
import com.shoppingbyktor.database.models.comment.CommentReviewRequest
import com.shoppingbyktor.database.models.comment.toCommentResponse
import com.shoppingbyktor.modules.comment.repository.CommentReviewRepo
import com.shoppingbyktor.utils.extension.alreadyExistException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and

/**
 * Controller for managing product reviews and ratings. Provides methods to get, add, update, and delete review ratings.
 */
class CommentReviewController : CommentReviewRepo {

    /**
     * Retrieves a list of reviews and ratings for a product.
     *
     * @param productId The ID of the product whose reviews and ratings are to be retrieved.
     * @return A list of reviews and ratings for the specified product.
     */
    override suspend fun getCommentReview(
        productId: Long
    ): List<CommentResponse> = query {
        val isProductIdExist =
            CommentReviewDAO.Companion.find { CommentReviewTable.productId eq productId }
        isProductIdExist.map {
            val user = UserDAO.Companion.find{UserTable.id eq it.userId}.single().response()
           it.response().toCommentResponse(user)
        }
    }

    /**
     * Adds a new review and rating for a product. If the user has already reviewed the product, an exception is thrown.
     *
     * @param userId The ID of the user adding the review and rating.
     * @param commentReview The review and rating details to be added.
     * @return The added review and rating.
     * @throws alreadyExistException If the user has already reviewed the specified product.
     */
    override suspend fun addCommentReview(userId: Long, commentReview: CommentReviewRequest): Any? = query {
        val isCommentReviewExist =
            CommentReviewDAO.Companion.find { CommentReviewTable.id eq userId and (CommentReviewTable.productId eq commentReview.product_id) }
                .singleOrNull()
        isCommentReviewExist?.let {
            throw it.productId.value.alreadyExistException()
        } ?: CommentReviewDAO.Companion.new {
            this.userId = EntityID(userId, CommentReviewTable)
            productId = EntityID(commentReview.product_id, CommentReviewTable)
            comment = commentReview.comment
            rate = commentReview.rate
        }.response()

        null
    }

    /**
     * Updates an existing review and rating.
     *
     * @param reviewId The ID of the review to be updated.
     * @param comment The updated review text.
     * @param rating The updated rating value.
     * @return The updated review and rating.
     * @throws comment.notFoundException() If the review ID does not exist.
     */
    override suspend fun updateCommentReview(
        reviewId: Long,
        comment: String,
        rate: Double
    ): Any? = query {
        val isCommentReviewExist =
            CommentReviewDAO.Companion.find { CommentReviewTable.id eq reviewId }
                .singleOrNull()

        isCommentReviewExist?.let {
            it.comment = comment
            it.rate = rate
            it.response()
        } ?: throw comment.notFoundException()

        null
    }

    /**
     * Deletes a review and rating by its ID.
     *
     * @param reviewId The ID of the review to be deleted.
     * @return The ID of the deleted review.
     * @throws reviewId.notFoundException() If the review ID does not exist.
     */
    override suspend fun deleteCommentReview(reviewId: Long): Any? = query {
        val isCommentReviewExist =
            CommentReviewDAO.Companion.find { CommentReviewTable.id eq reviewId }
                .singleOrNull()
        isCommentReviewExist?.let {
            it.delete()
            reviewId.toString()
        } ?: throw reviewId.notFoundException()

        null
    }
}