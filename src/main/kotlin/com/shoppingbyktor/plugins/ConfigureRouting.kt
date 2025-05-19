package com.shoppingbyktor.plugins

import com.shoppingbyktor.modules.auth.routes.authRoutes
import com.shoppingbyktor.modules.auth.controller.AuthController
import com.shoppingbyktor.modules.brand.routes.brandRoutes
import com.shoppingbyktor.modules.brand.controller.BrandController
import com.shoppingbyktor.modules.cart.routes.cartRoutes
import com.shoppingbyktor.modules.cart.controller.CartController
import com.shoppingbyktor.modules.consent.routes.consentRoutes
import com.shoppingbyktor.modules.consent.controller.ConsentController
import com.shoppingbyktor.modules.home.controller.HomeController
import com.shoppingbyktor.modules.home.routes.homeRoutes
import com.shoppingbyktor.modules.order.routes.orderRoutes
import com.shoppingbyktor.modules.order.controller.OrderController
import com.shoppingbyktor.modules.payment.routes.paymentRoutes
import com.shoppingbyktor.modules.payment.controller.PaymentController
import com.shoppingbyktor.modules.policy.routes.policyRoutes
import com.shoppingbyktor.modules.policy.controller.PolicyController
import com.shoppingbyktor.modules.product.routes.productRoutes
import com.shoppingbyktor.modules.product.controller.ProductController
import com.shoppingbyktor.modules.productcategory.routes.productCategoryRoutes
import com.shoppingbyktor.modules.productcategory.controller.ProductCategoryController
import com.shoppingbyktor.modules.productsubcategory.routes.productSubCategoryRoutes
import com.shoppingbyktor.modules.productsubcategory.controller.ProductSubCategoryController
import com.shoppingbyktor.modules.profile.routes.profileRoutes
import com.shoppingbyktor.modules.profile.controller.ProfileController
import com.shoppingbyktor.modules.review_rating.routes.reviewRatingRoutes
import com.shoppingbyktor.modules.review_rating.controller.ReviewRatingController
import com.shoppingbyktor.modules.search.controller.SearchController
import com.shoppingbyktor.modules.search.routes.searchRoutes
import com.shoppingbyktor.modules.shipping.routes.shippingRoutes
import com.shoppingbyktor.modules.shipping.controller.ShippingController
import com.shoppingbyktor.modules.shop.controller.ShopController
import com.shoppingbyktor.modules.shop.routes.shopRoutes
import com.shoppingbyktor.modules.shopcategory.routes.shopCategoryRoutes
import com.shoppingbyktor.modules.shopcategory.controller.ShopCategoryController
import com.shoppingbyktor.modules.wishlist.routes.wishListRoutes
import com.shoppingbyktor.modules.wishlist.controller.WishListController
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRoute() {
    val authController: AuthController by inject()
    val userProfileController: ProfileController by inject()
    val shopCategoryController: ShopCategoryController by inject()
    val shopController: ShopController by inject()
    val brandController: BrandController by inject()
    val productCategoryController: ProductCategoryController by inject()
    val productSubCategoryController: ProductSubCategoryController by inject()
    val productController: ProductController by inject()
    val reviewRatingController: ReviewRatingController by inject()
    val cartController: CartController by inject()
    val wishListController: WishListController by inject()
    val shippingController: ShippingController by inject()
    val orderController: OrderController by inject()
    val paymentController: PaymentController by inject()
    val policyController: PolicyController by inject()
    val consentController: ConsentController by inject()
    val homeController: HomeController by inject()
    val searchController: SearchController by inject()
    routing {

        staticResources("/product-image", "product-image")
        staticResources("/category-image", "category-image")

        authRoutes(authController)
        profileRoutes(userProfileController)
        shopCategoryRoutes(shopCategoryController)
        shopRoutes(shopController)
        brandRoutes(brandController)
        productCategoryRoutes(productCategoryController)
        productSubCategoryRoutes(productSubCategoryController)
        productRoutes(productController)
        searchRoutes(searchController)
        reviewRatingRoutes(reviewRatingController)
        cartRoutes(cartController)
        wishListRoutes(wishListController)
        shippingRoutes(shippingController)
        orderRoutes(orderController)
        paymentRoutes(paymentController)
        policyRoutes(policyController)
        consentRoutes(consentController)
        homeRoutes(homeController)
    }
}
