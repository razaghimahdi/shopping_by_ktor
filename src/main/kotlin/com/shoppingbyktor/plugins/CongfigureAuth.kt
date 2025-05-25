package com.shoppingbyktor.plugins

import com.shoppingbyktor.modules.auth.controller.JwtController
import com.shoppingbyktor.database.models.user.body.JwtTokenRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.slf4j.LoggerFactory

fun Application.configureAuth() {
    install(Authentication) {

        jwt("jwt") {
            provideJwtAuthConfig(this)
        }
    }
}

fun provideJwtAuthConfig(jwtConfig: JWTAuthenticationProvider.Config) {
    val logger = LoggerFactory.getLogger("MyAppLogger")
    logger.info("provideJwtAuthConfig jwtConfig realm: "+jwtConfig.realm)
    logger.info("provideJwtAuthConfig jwtConfig name: "+jwtConfig.name)

    jwtConfig.verifier(JwtController.verifier)
    jwtConfig.realm = "shoppingbyktor"
    jwtConfig.validate {
        logger.info("provideJwtAuthConfig jwtConfig.validate: " )
        val userId = it.payload.getClaim("userId").asLong()
        val email = it.payload.getClaim("email").asString()
        logger.info("Token claims: userId=$userId, email=$email")

        JwtTokenRequest(userId, email)
    }
}
