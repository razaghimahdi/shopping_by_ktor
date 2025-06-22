package com.shoppingbyktor.modules.profile.controller

import com.shoppingbyktor.database.entities.UserProfile
import com.shoppingbyktor.database.entities.UserProfileTable
import com.shoppingbyktor.database.entities.UsersProfileDAO
import com.shoppingbyktor.database.models.user.body.UserProfileRequest
import com.shoppingbyktor.modules.profile.repository.ProfileRepo
import com.shoppingbyktor.utils.AppConstants
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Controller for managing user profiles. Provides methods to retrieve, update, and change user profile details and images.
 */
class ProfileController : ProfileRepo {

    init {
        // Create the profile image directory if it does not exist.
        if (!File(AppConstants.ImageFolder.PROFILE_IMAGE_LOCATION).exists()) {
            File(AppConstants.ImageFolder.PROFILE_IMAGE_LOCATION).mkdirs()
        }
    }

    /**
     * Retrieves the user profile based on the given user ID.
     *
     * @param userId The ID of the user whose profile is to be retrieved.
     * @return The user profile corresponding to the given user ID.
     * @throws userId.notFoundException() If no user profile is found for the given user ID.
     */
    override suspend fun getProfile(userId: Long): UserProfile = query {
        val isProfileExist =
            UsersProfileDAO.Companion.find { UserProfileTable.userId eq userId }.toList().singleOrNull()
        isProfileExist?.response() ?: throw userId.notFoundException()
    }

    /**
     * Updates the user profile details.
     *
     * @param userId The ID of the user whose profile is to be updated.
     * @param userProfile The new profile details to update, can be null to keep current values.
     * @return The updated user profile.
     * @throws userId.notFoundException() If no user profile is found for the given user ID.
     */
    override suspend fun updateProfile(userId: Long, userProfile: UserProfileRequest?): UserProfile = query {
        val userProfileEntity =
            UsersProfileDAO.Companion.find { UserProfileTable.userId eq userId }.toList().singleOrNull()
        userProfileEntity?.let {
            it.name = userProfile?.name ?: it.name
            it.age = userProfile?.age ?: it.age
            it.response()
        } ?: throw userId.notFoundException()
    }

    /**
     * Updates the user's profile image and replaces the old one if it exists.
     *
     * @param userId The ID of the user whose profile image is to be updated.
     * @param profileImage The new profile image file name.
     * @param name The new profile name. Can be null if no name is provided.
     * @param age The new profile age. Can be null if no age is provided.
     * @return The status.
     * @throws userId.notFoundException() If no user profile is found for the given user ID.
     */
    override suspend fun updateProfileImage(userId: Long, imageFileName: String?, name: String?, age: Int?): Boolean = query {
        val userProfileEntity = UsersProfileDAO.find { UserProfileTable.userId eq userId }.singleOrNull()
            ?: throw userId.notFoundException()

        // Delete old image if needed
        userProfileEntity.image?.let {
            Files.deleteIfExists(Paths.get("${AppConstants.ImageFolder.PROFILE_IMAGE_LOCATION}$it"))
        }

        if (imageFileName != null) {
            userProfileEntity.image = imageFileName
        }
        if (name != null) {
            userProfileEntity.name = name
        }
        if (age != null) {
            userProfileEntity.age = age
        }
        userProfileEntity.response()

        true
    }

}