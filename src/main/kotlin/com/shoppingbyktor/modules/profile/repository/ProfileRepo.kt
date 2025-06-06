package com.shoppingbyktor.modules.profile.repository

import com.shoppingbyktor.database.entities.UserProfile
import com.shoppingbyktor.database.models.user.body.UserProfileRequest

interface ProfileRepo {
    /**
     * Retrieves the user profile.
     *
     * @param userId The unique identifier of the user.
     * @return The user's profile.
     */
    suspend fun getProfile(userId: Long): UserProfile

    /**
     * Updates the user profile information.
     *
     * @param userId The unique identifier of the user.
     * @param profileRequest The updated profile data. Can be null if no update is provided.
     * @return The updated user profile.
     */
    suspend fun updateProfile(userId: Long, profileRequest: UserProfileRequest?): UserProfile

    /**
     * Updates the user's profile image.
     *
     * @param userId The unique identifier of the user.
     * @param imageUrl The new profile image URL. Can be null if no image is provided.
     * @return The updated user profile.
     */
    suspend fun updateProfileImage(userId: Long, imageUrl: String?): UserProfile
}