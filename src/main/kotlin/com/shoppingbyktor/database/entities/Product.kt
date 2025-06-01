package com.shoppingbyktor.database.entities

import com.shoppingbyktor.database.entities.base.BaseIntEntity
import com.shoppingbyktor.database.entities.base.BaseIntEntityClass
import com.shoppingbyktor.database.entities.base.BaseIntIdTable
import org.jetbrains.exposed.dao.id.EntityID

object ProductTable : BaseIntIdTable("product") {
    val title = text("title")
    val description = text("description")
    val categoryId = reference("category_id", ProductCategoryTable.id)
    val subCategoryId = reference("sub_category_id", ProductSubCategoryTable.id).nullable()
    val brandId = reference("brand_id", BrandTable.id).nullable()
    val stockQuantity = integer("stock_quantity") // Number of products in stock
    val minOrderQuantity = integer("min_order_quantity").default(1) // Minimum quantity required for purchase
    val soldCount = integer("sold_count").default(0) // number of sold
    val price = long("price")
    val rate = double("rate")
    val discountPrice = double("discount_price").nullable()
    val videoLink = text("video_link").nullable()
    val hotDeal = bool("hot_deal").default(false) // Whether it's a hot deal or not
    val featured = bool("featured").default(false) // Whether the product is featured or not
    val gallery = varchar("gallery", 1000) // Comma-separated image URLs for the product
    val status = enumerationByName("status", 50, ProductStatus::class).default(ProductStatus.ACTIVE) // Product status

    enum class ProductStatus {
        ACTIVE, // Product is available for purchase
        OUT_OF_STOCK // Product is not available
    }

}

class ProductDAO(id: EntityID<Long>) : BaseIntEntity(id, ProductTable) {
    companion object : BaseIntEntityClass<ProductDAO>(ProductTable)

    var categoryId by ProductTable.categoryId
    var subCategoryId by ProductTable.subCategoryId
    var brandId by ProductTable.brandId
    var title by ProductTable.title
    var description by ProductTable.description
    var minOrderQuantity by ProductTable.minOrderQuantity
    var stockQuantity by ProductTable.stockQuantity
    var price by ProductTable.price
    var discountPrice by ProductTable.discountPrice
    var videoLink by ProductTable.videoLink
    var hotDeal by ProductTable.hotDeal
    var featured by ProductTable.featured
    var gallery by ProductTable.gallery

    //  private val images = gallery.removePrefix("[").removeSuffix("]").split(",").map { it.trim() }
    // var image = images.firstOrNull() ?: ""
    var status by ProductTable.status
    var rate by ProductTable.rate
    var soldCount by ProductTable.soldCount
    fun response(): Product {
        val gallery = gallery.removePrefix("[").removeSuffix("]").split(",").map { it.trim() }
        return Product(
            id.value,
            categoryId.value,
            subCategoryId?.value,
            brandId?.value,
            title,
            description,
            minOrderQuantity,
            stockQuantity,
            price,
            discountPrice,
            videoLink,
            hotDeal,
            featured,
            gallery,
            status,
            rate,
            soldCount,
            gallery.firstOrNull() ?: "",
            0
        )
    }
    fun response(count: Int): Product {
        val gallery = gallery.removePrefix("[").removeSuffix("]").split(",").map { it.trim() }
        return Product(
            id.value,
            categoryId.value,
            subCategoryId?.value,
            brandId?.value,
            title,
            description,
            minOrderQuantity,
            stockQuantity,
            price,
            discountPrice,
            videoLink,
            hotDeal,
            featured,
            gallery,
            status,
            rate,
            soldCount,
            gallery.firstOrNull() ?: "",
            count
        )
    }
}

data class Product(
    val id: Long,
    val categoryId: Long,
    val subCategoryId: Long?,
    val brandId: Long?,
    val title: String,
    val description: String,
    val minOrderQuantity: Int,
    val stockQuantity: Int,
    val price: Long,
    val discountPrice: Double?,
    val videoLink: String?,
    val hotDeal: Boolean?,
    val featured: Boolean,
    val gallery: List<String>,
    val status: ProductTable.ProductStatus,
    val rate: Double,
    val soldCount: Int,
    val image: String,
    val count: Int,
)