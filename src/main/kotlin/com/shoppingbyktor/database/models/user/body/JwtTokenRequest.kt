package com.shoppingbyktor.database.models.user.body

import io.ktor.server.auth.*

data class JwtTokenRequest(val userId: Long, val email: String) : Principal