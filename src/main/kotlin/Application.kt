package org.example

import io.ktor.server.application.*
import org.example.infrastructure.database.configureDatabases
import org.example.infrastructure.routes.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureFrameworks()
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureHTTP()
    configureRouting()
}
