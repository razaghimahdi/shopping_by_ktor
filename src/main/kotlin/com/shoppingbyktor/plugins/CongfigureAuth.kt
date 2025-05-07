package com.shoppingbyktor.plugins

import com.shoppingbyktor.modules.auth.controller.JwtController
import com.shoppingbyktor.database.models.user.body.JwtTokenRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuth() {
    install(Authentication) {
        /**
         * Setup the JWT authentication to be used in [Routing].
         * If the token is valid, the corresponding [User] is fetched from the database.
         * The [User] can then be accessed in each [ApplicationCall].
         */
        jwt (RoleManagement.CUSTOMER.role){
            provideJwtAuthConfig(this, RoleManagement.CUSTOMER)
        }
    }
}

fun provideJwtAuthConfig(jwtConfig: JWTAuthenticationProvider.Config, userRole: RoleManagement) {
    jwtConfig.verifier(JwtController.verifier)
    jwtConfig.realm = "shoppingbyktor"
    jwtConfig.validate {
        val userId = it.payload.getClaim("userId").asString()
        val email = it.payload.getClaim("email").asString()
        val userType = it.payload.getClaim("userType").asString()
        if (userType == userRole.role) {
            JwtTokenRequest(userId, email, userType)
        } else null
    }
}

enum class RoleManagement(val role: String) {
    CUSTOMER("customer")
}