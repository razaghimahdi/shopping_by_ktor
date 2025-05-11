package com.shoppingbyktor.modules.home.controller

import com.shoppingbyktor.database.entities.*
import com.shoppingbyktor.database.models.home.response.FlashSaleResponse
import com.shoppingbyktor.database.models.home.response.HomeResponse
import com.shoppingbyktor.modules.home.repository.HomeRepo
import com.shoppingbyktor.utils.AppConstants
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter

class HomeController : HomeRepo {



    /**
     * Initializes the product image and category image folder if it does not exist.
     */
    init {
        if (!File(AppConstants.ImageFolder.PRODUCT_IMAGE_LOCATION).exists()) {
            File(AppConstants.ImageFolder.PRODUCT_IMAGE_LOCATION).mkdirs()
        }
        if (!File(AppConstants.ImageFolder.CATEGORY_IMAGE_LOCATION).exists()) {
            File(AppConstants.ImageFolder.CATEGORY_IMAGE_LOCATION).mkdirs()
        }
    }



    /**
     * Retrieves some date that is going to show on HomeScreen.
     *
     * @param productQuery The filter request containing the parameters to filter products.
     * @param bannersQuery List of image as header in HomeScreen.
     * @param categoryQuery List of categories in HomeScreen.
     * @return HomeResponse .
     */
    override suspend fun home(): HomeResponse = query {
        val productsQuery = ProductTable.selectAll()
        val bannersQuery = BannerTable.selectAll()
        val categoryQuery = ProductCategoryTable.selectAll()


        val banners = bannersQuery.map {
            BannerDAO.Companion.wrapRow(it).response()
        }
        val categories = categoryQuery.map {
            ProductCategoryDAO.Companion.wrapRow(it).response()
        }

        val newestProduct = productsQuery.orderBy(ProductTable.createdAt, SortOrder.DESC)
            .limit(15)
            .map { ProductDAO.wrapRow(it).response() }
        val mostSaleProduct = productsQuery.orderBy(ProductTable.soldCount, SortOrder.DESC)
            .limit(15)
            .map { ProductDAO.wrapRow(it).response() }
        val flashSaleProduct = productsQuery.orderBy(ProductTable.createdAt, SortOrder.ASC)
            .limit(15)
            .map { ProductDAO.wrapRow(it).response() }
        val now = Instant.now()
        val flashDate = now.plus(java.time.Duration.ofHours(4).plusMinutes(20))
        val formatedFlashDate = DateTimeFormatter.ISO_INSTANT.format(flashDate)

        HomeResponse(
            banners = banners,
            categories = categories,
            newest_product = newestProduct,
            flash_sale = FlashSaleResponse(products = flashSaleProduct, expired_at = formatedFlashDate),
            most_sale = mostSaleProduct
        )
    }
}