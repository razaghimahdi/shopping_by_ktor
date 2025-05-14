package com.shoppingbyktor.database

import com.shoppingbyktor.database.entities.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource


fun configureDataBase() {
    initDB()
    transaction {
        addLogger(StdOutSqlLogger)
        create(
            UserTable,
            UserProfileTable,
            ShopTable,
            ShopCategoryTable,
            ProductTable,
            ReviewRatingTable,
            ProductCategoryTable,
            ProductSubCategoryTable,
            BrandTable,
            CartItemTable,
            OrderTable,
            OrderItemTable,
            WishListTable,
            ShippingTable,
            PaymentTable,
            PolicyDocumentTable,
            PolicyConsentTable,
            BannerTable
        )
    }

    seedProducts()
}

private fun initDB() {
    // database connection is handled from hikari properties
    val config = HikariConfig("/hikari.properties")
    val dataSource = HikariDataSource(config)
    runFlyway(dataSource)
    Database.connect(dataSource)
}

//private fun runFlyway(datasource: DataSource) {
//    val flyway = Flyway.configure()
//        .dataSource(datasource)
//        .baselineOnMigrate(true)
//        .load()
//    try {
//        flyway.migrate()
//    } catch (e: Exception) {
//        throw e
//    }
//}



private fun runFlyway(datasource: DataSource) {
    val flyway = Flyway.configure()
        .schemas("shopdev")
        .defaultSchema("shopdev")
        .dataSource(datasource).load()
    try {
        flyway.info()
        flyway.migrate()
    } catch (e: Exception) {
        throw e
    }
}