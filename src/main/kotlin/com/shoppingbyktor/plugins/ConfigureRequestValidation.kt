package com.shoppingbyktor.plugins

import com.shoppingbyktor.database.models.PaymentRequest
import com.shoppingbyktor.database.models.WisListRequest
import com.shoppingbyktor.database.models.bands.BrandRequest
import com.shoppingbyktor.database.models.cart.CartRequest
import com.shoppingbyktor.database.models.category.ProductCategoryRequest
import com.shoppingbyktor.database.models.order.OrderRequest
import com.shoppingbyktor.database.models.product.request.ProductRequest
import com.shoppingbyktor.database.models.product.request.ProductSearchRequest
import com.shoppingbyktor.database.models.shipping.ShippingRequest
import com.shoppingbyktor.database.models.shop.ShopCategoryRequest
import com.shoppingbyktor.database.models.shop.ShopRequest
import com.shoppingbyktor.database.models.subcategory.ProductSubCategoryRequest
import com.shoppingbyktor.database.models.user.body.LoginRequest
import com.shoppingbyktor.database.models.user.body.RegisterRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<LoginRequest> { login ->
            login.validation()
            ValidationResult.Valid
        }
        validate<RegisterRequest> { register ->
            register.validation()
            ValidationResult.Valid
        }
        validate<ProductSearchRequest> { search ->
            search.validation()
            ValidationResult.Valid
        }
        validate<ProductCategoryRequest> { productCategory ->
            productCategory.validation()
            ValidationResult.Valid
        }
        validate<ProductSubCategoryRequest> { productSubCategory ->
            productSubCategory.validation()
            ValidationResult.Valid
        }
        validate<ShopRequest> { shop ->
            shop.validation()
            ValidationResult.Valid
        }
        validate<ShopCategoryRequest> { shopCategory ->
            shopCategory.validation()
            ValidationResult.Valid
        }
        validate<BrandRequest> { brand ->
            brand.validation()
            ValidationResult.Valid
        }
        validate<ProductRequest> { product ->
            product.validation()
            ValidationResult.Valid
        }
        validate<ShippingRequest> { shipping ->
            shipping.validation()
            ValidationResult.Valid
        }
        validate<OrderRequest> { order ->
            order.validation()
            ValidationResult.Valid
        }
        validate<CartRequest> { cart ->
            cart.validation()
            ValidationResult.Valid
        }
        validate<WisListRequest> { wishlist ->
            wishlist.validation()
            ValidationResult.Valid
        }
        validate<PaymentRequest> { payment ->
            payment.validation()
            ValidationResult.Valid
        }
    }
}