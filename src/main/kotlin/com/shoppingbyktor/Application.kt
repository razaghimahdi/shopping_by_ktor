package com.shoppingbyktor

import com.shoppingbyktor.database.configureDataBase
import com.shoppingbyktor.plugins.*
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory

fun main() {
    val config = HoconApplicationConfig(ConfigFactory.load("application.conf"))
    val port = config.property("ktor.deployment.port").getString().toInt()
    val host = config.property("ktor.deployment.host").getString()
    val logger = LoggerFactory.getLogger("MyAppLogger")
    logger.info("main")
    embeddedServer(Netty, port = port, host = host) {
        configureDataBase()
        configureBasic()
        configureKoin()
        configureRequestValidation()
        configureAuth()
        configureSwagger()
        configureStatusPage()
        configureRoute()
    }.start(wait = true)
}
