package com.shoppingbyktor.plugins

import com.shoppingbyktor.modules.auth.controller.JwtController
import com.shoppingbyktor.database.models.user.body.JwtTokenRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuth() {
    install(Authentication) {

    }
}

fun provideJwtAuthConfig(jwtConfig: JWTAuthenticationProvider.Config) {
    jwtConfig.verifier(JwtController.verifier)
    jwtConfig.realm = "shoppingbyktor"
    jwtConfig.validate {
        val userId = it.payload.getClaim("userId").asString()
        val email = it.payload.getClaim("email").asString()
        val userType = it.payload.getClaim("userType").asString()
        JwtTokenRequest(userId, email, userType)
    }
}
