package com.shoppingbyktor.modules.shop.routes

import com.shoppingbyktor.modules.shop.controller.ShopController
import io.ktor.server.routing.*

/**
 * Route for managing shops in the application.
 *
 * @param shopController The controller responsible for handling shop-related operations.
 */
fun Route.shopRoutes(shopController: ShopController) {
    route("shop") {


    }
}