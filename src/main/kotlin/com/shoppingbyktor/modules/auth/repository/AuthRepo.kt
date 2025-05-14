package com.shoppingbyktor.modules.auth.repository

import com.shoppingbyktor.database.entities.ChangePassword
import com.shoppingbyktor.database.models.user.body.ForgetPasswordRequest
import com.shoppingbyktor.database.models.user.body.LoginRequest
import com.shoppingbyktor.database.models.user.body.RegisterRequest
import com.shoppingbyktor.database.models.user.body.ResetRequest

interface AuthRepo {
    /**
     * Registers a new user.
     *
     * @param request The registration details.
     * @return The registration response.
     */
    suspend fun register(registerRequest: RegisterRequest): String?

    /**
     * Authenticates a user and returns a login response.
     *
     * @param request The login credentials.
     * @return The login response.
     */
    suspend fun login(loginRequest: LoginRequest): String?

    /**
     * Changes the password for a user.
     *
     * @param userId The unique identifier of the user.
     * @param request The password change details.
     * @return `true` if the password change was successful, `false` otherwise.
     */
    suspend fun changePassword(userId: Long, changePassword: ChangePassword): Boolean

    /**
     * Sends a verification code for password reset.
     *
     * @param request The request containing user details.
     * @return The verification code sent to the user.
     */
    suspend fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest): String

    /**
     * Verifies the password reset code.
     *
     * @param request The request containing the verification details.
     * @return A status code representing the verification result.
     */
    suspend fun resetPassword(resetPasswordRequest: ResetRequest): Int
}