package com.shoppingbyktor.modules.auth.controller

import at.favre.lib.crypto.bcrypt.BCrypt
import com.shoppingbyktor.database.entities.ChangePassword
import com.shoppingbyktor.database.entities.UserDAO
import com.shoppingbyktor.database.entities.UserTable
import com.shoppingbyktor.database.models.user.body.ForgetPasswordRequest
import com.shoppingbyktor.database.models.user.body.LoginRequest
import com.shoppingbyktor.database.models.user.body.RegisterRequest
import com.shoppingbyktor.database.models.user.body.ResetRequest
import com.shoppingbyktor.modules.auth.repository.AuthRepo
import com.shoppingbyktor.utils.CommonException
import com.shoppingbyktor.utils.FillInputCorrect
import com.shoppingbyktor.utils.PasswordNotMatch
import com.shoppingbyktor.utils.UserNotExistException
import com.shoppingbyktor.utils.extension.alreadyExistException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query

class AuthController : AuthRepo {
    /**
     * Registers a new user with the given [registerRequest].
     * Throws an exception if the user already exists.
     *
     * @param registerRequest The request containing user details.
     * @return The response containing the token.
     */
    override suspend fun register(registerRequest: RegisterRequest): String? = query {

        // Check if user exists with the same email
        val userEntity =
            UserDAO.Companion.find { (UserTable.email eq registerRequest.email) }
                .toList().singleOrNull()

        when {
            userEntity != null -> throw registerRequest.email.alreadyExistException()
            registerRequest.email.isEmpty() || registerRequest.name.isEmpty() || registerRequest.password.isEmpty()->{
                throw FillInputCorrect()
            }
            else -> {
                // Create new user
                val inserted = UserDAO.Companion.new {
                    email = registerRequest.email
                    name = registerRequest.name
                    password = BCrypt.withDefaults().hashToString(12, registerRequest.password.toCharArray())
                }
                inserted.loggedInWithToken().accessToken
            }
        }

    }

    /**
     * Logs in a user with the given [loginRequest].
     * Throws an exception if the user does not exist or the password is incorrect.
     *
     * @param loginRequest The request containing login credentials.
     * @return The response containing the authentication token.
     */
    override suspend fun login(loginRequest: LoginRequest): String? = query {
        val userEntity =
            UserDAO.Companion.find { UserTable.email eq loginRequest.email }.toList().singleOrNull()

        userEntity?.let {
            if (BCrypt.verifyer().verify(
                    loginRequest.password.toCharArray(), it.password
                ).verified
            ) {
                it.loggedInWithToken().accessToken
            } else {
                throw PasswordNotMatch()
            }
        } ?: throw loginRequest.email.notFoundException()
    }


    /**
     * Changes the password for a user.
     * Throws an exception if the user does not exist or the old password is incorrect.
     *
     * @param userId The ID of the user.
     * @param changePassword The request containing the old and new passwords.
     * @return `true` if the password is changed successfully, otherwise `false`.
     */
    override suspend fun changePassword(userId: String, changePassword: ChangePassword): Boolean = query {
        val userEntity = UserDAO.Companion.find { UserTable.id eq userId }.toList().singleOrNull()
        userEntity?.let {
            if (BCrypt.verifyer().verify(changePassword.oldPassword.toCharArray(), it.password).verified) {
                // Check if new password is same as old password
                if (changePassword.oldPassword == changePassword.newPassword) {
                    throw CommonException("New password cannot be the same as old password")
                }
                it.password = BCrypt.withDefaults().hashToString(12, changePassword.newPassword.toCharArray())
                true
            } else {
                false
            }
        } ?: throw UserNotExistException()
    }

    /**
     * Sends a password reset code to the user.
     * Throws an exception if the user does not exist.
     *
     * @param forgetPasswordRequest The request containing the user's email.
     * @return The verification code sent to the user.
     */
    override suspend fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest): String = query {
        // Find all users with the given email
        val userEntities = UserDAO.Companion.find { UserTable.email eq forgetPasswordRequest.email }.toList()

        if (userEntities.isEmpty()) {
            throw forgetPasswordRequest.email.notFoundException()
        }
//
//        // Find the specific user with the given email and userType
//        val specificUser = userEntities.find { it.userType == forgetPasswordRequest.userType }
//        specificUser?.let {
//            val otp = generateOTP()
//            it.otpCode = otp
//            otp
//        }
//            ?: throw "${forgetPasswordRequest.email} not found for ${forgetPasswordRequest.userType} role".notFoundException()

        ""
    }

    /**
     * Verifies the password reset code and updates the password if the code is valid.
     * If the verification code matches, the password is updated and the code is cleared.
     * Returns a constant indicating whether the operation was successful or not.
     *
     * @param resetPasswordRequest The request containing email, verification code, and new password.
     * @return `FOUND` if the verification code is correct and the password is updated, otherwise `NOT_FOUND`.
     * @throws Exception if the user does not exist.
     */
    override suspend fun resetPassword(resetPasswordRequest: ResetRequest): Int = query {
        // Find all users with the given email
        val userEntities = UserDAO.Companion.find { UserTable.email eq resetPasswordRequest.email }.toList()

        if (userEntities.isEmpty()) {
            throw resetPasswordRequest.email.notFoundException()
        }
//
//        // Find the specific user with the given email and userType
//        val userEntity = userEntities.find { it.userType == resetPasswordRequest.userType }
//            ?: throw "${resetPasswordRequest.email} not found for ${resetPasswordRequest.userType} role".notFoundException()
//
//        // Verify the code and update the password
//        if (userEntity.otpCode == resetPasswordRequest.verificationCode) {
//            // Check if new password is same as current password
//            if (BCrypt.verifyer()
//                    .verify(resetPasswordRequest.newPassword.toCharArray(), userEntity.password).verified
//            ) {
//                throw CommonException("New password cannot be the same as current password")
//            }
//            userEntity.password = BCrypt.withDefaults().hashToString(12, resetPasswordRequest.newPassword.toCharArray())
//            AppConstants.DataBaseTransaction.FOUND
//        } else {
//            AppConstants.DataBaseTransaction.NOT_FOUND
//        }
        0
    }
}