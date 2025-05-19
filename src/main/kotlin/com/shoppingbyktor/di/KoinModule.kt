package com.shoppingbyktor.di

import com.shoppingbyktor.modules.auth.controller.AuthController
import com.shoppingbyktor.modules.brand.controller.BrandController
import com.shoppingbyktor.modules.cart.controller.CartController
import com.shoppingbyktor.modules.consent.controller.ConsentController
import com.shoppingbyktor.modules.home.controller.HomeController
import com.shoppingbyktor.modules.order.controller.OrderController
import com.shoppingbyktor.modules.payment.controller.PaymentController
import com.shoppingbyktor.modules.policy.controller.PolicyController
import com.shoppingbyktor.modules.product.controller.ProductController
import com.shoppingbyktor.modules.productcategory.controller.ProductCategoryController
import com.shoppingbyktor.modules.productsubcategory.controller.ProductSubCategoryController
import com.shoppingbyktor.modules.profile.controller.ProfileController
import com.shoppingbyktor.modules.review_rating.controller.ReviewRatingController
import com.shoppingbyktor.modules.search.controller.SearchController
import com.shoppingbyktor.modules.shipping.controller.ShippingController
import com.shoppingbyktor.modules.shop.controller.ShopController
import com.shoppingbyktor.modules.shopcategory.controller.ShopCategoryController
import com.shoppingbyktor.modules.wishlist.controller.WishListController
import org.koin.dsl.module

val controllerModule = module {
    single { BrandController() }
    single { CartController() }
    single { OrderController() }
    single { OrderController() }
    single { ProductController() }
    single { SearchController() }
    single { HomeController() }
    single { ProductCategoryController() }
    single { ProductSubCategoryController() }
    single { ShippingController() }
    single { ShopController() }
    single { ShopCategoryController() }
    single { AuthController() }
    single { ProfileController() }
    single { WishListController() }
    single { PaymentController() }
    single { ReviewRatingController() }
    single { PolicyController() }
    single { ConsentController() }
}