package com.shoppingbyktor.database

import com.shoppingbyktor.database.entities.BrandTable
import com.shoppingbyktor.database.entities.CartItemTable
import com.shoppingbyktor.database.entities.OrderItemTable
import com.shoppingbyktor.database.entities.OrderTable
import com.shoppingbyktor.database.entities.PaymentTable
import com.shoppingbyktor.database.entities.PolicyConsentTable
import com.shoppingbyktor.database.entities.PolicyDocumentTable
import com.shoppingbyktor.database.entities.ProductCategoryTable
import com.shoppingbyktor.database.entities.ProductSubCategoryTable
import com.shoppingbyktor.database.entities.ProductTable
import com.shoppingbyktor.database.entities.ReviewRatingTable
import com.shoppingbyktor.database.entities.ShippingTable
import com.shoppingbyktor.database.entities.ShopCategoryTable
import com.shoppingbyktor.database.entities.ShopTable
import com.shoppingbyktor.database.entities.UserProfileTable
import com.shoppingbyktor.database.entities.UserTable
import com.shoppingbyktor.database.entities.WishListTable
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
            PolicyConsentTable
        )
    }
}

private fun initDB() {
    // database connection is handled from hikari properties
    val config = HikariConfig("/hikari.properties")
    val dataSource = HikariDataSource(config)
    runFlyway(dataSource)
    Database.connect(dataSource)
}

private fun runFlyway(datasource: DataSource) {
    val flyway = Flyway.configure()
        .dataSource(datasource)
        .baselineOnMigrate(true)
        .load()
    try {
        flyway.migrate()
    } catch (e: Exception) {
        throw e
    }
}
