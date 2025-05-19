package com.shoppingbyktor.modules.product.controller

import com.shoppingbyktor.database.entities.Product
import com.shoppingbyktor.database.entities.ProductDAO
import com.shoppingbyktor.database.entities.ProductTable
import com.shoppingbyktor.database.models.product.request.ProductRequest
import com.shoppingbyktor.database.models.product.request.ProductSearchRequest
import com.shoppingbyktor.database.models.product.request.ProductWithFilterRequest
import com.shoppingbyktor.database.models.product.request.UpdateProduct
import com.shoppingbyktor.modules.product.repository.ProductRepo
import com.shoppingbyktor.utils.AppConstants
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import java.io.File

/**
 * Controller for managing products.
 */
class ProductController : ProductRepo {

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
     * Creates a new product.
     *
     * @param userId The ID of the user creating the product.
     * @param productRequest The request object containing product details.
     * @return The created product entity.
     */
    override suspend fun createProduct(userId: Long, productRequest: ProductRequest): Product = query {
        ProductDAO.Companion.new {
            categoryId = EntityID(productRequest.categoryId, ProductTable)
            subCategoryId = productRequest.subCategoryId?.let { EntityID(productRequest.subCategoryId, ProductTable) }
            brandId = productRequest.brandId?.let { EntityID(productRequest.brandId, ProductTable) }
            title = productRequest.title
            description = productRequest.description
            price = productRequest.price
            discountPrice = productRequest.discountPrice
            stockQuantity = productRequest.stockQuantity
            videoLink = productRequest.videoLink
            hotDeal = productRequest.hotDeal
            featured = productRequest.featured
            gallery = productRequest.gallery.toString()
        }.response()
    }

    /**
     * Updates an existing product's details.
     *
     * @param userId The ID of the user updating the product.
     * @param productId The ID of the product to be updated.
     * @param updateProduct The update request object containing new product details.
     * @return The updated product entity.
     * @throws Exception if the product with the provided ID is not found.
     */
    override suspend fun updateProduct(userId: Long, productId: Long, updateProduct: UpdateProduct): Product =
        query {
            val isProductExist =
                ProductDAO.Companion.find { (ProductTable.id eq productId) }.toList()
                    .singleOrNull()
            isProductExist?.apply {
                categoryId =
                    updateProduct.categoryId?.let { EntityID(updateProduct.categoryId, ProductTable) } ?: categoryId
                subCategoryId = updateProduct.subCategoryId?.let { EntityID(updateProduct.subCategoryId, ProductTable) }
                    ?: subCategoryId
                brandId = updateProduct.brandId?.let { EntityID(updateProduct.brandId, ProductTable) } ?: brandId
                title = updateProduct.title ?: title
                description = updateProduct.description ?: description
                price = updateProduct.price ?: price
                discountPrice = updateProduct.discountPrice ?: discountPrice
                stockQuantity = updateProduct.stockQuantity ?: stockQuantity
                videoLink = updateProduct.videoLink ?: videoLink
                hotDeal = updateProduct.hotDeal ?: hotDeal
                featured = updateProduct.featured ?: featured
                gallery = if (updateProduct.gallery.isEmpty()) gallery else updateProduct.gallery.toString()
            }?.response() ?: throw productId.notFoundException()
        }

    /**
     * Retrieves a list of products based on the provided filter criteria.
     *
     * @param productQuery The filter request containing the parameters to filter products.
     * @return A list of products matching the provided filters.
     */
    override suspend fun getProducts(productQuery: ProductWithFilterRequest): List<Product> = query {
        val query = ProductTable.selectAll()
        productQuery.maxPrice?.let {
            query.andWhere { ProductTable.price lessEq it }
        }
        productQuery.minPrice?.let {
            query.andWhere {
                ProductTable.price greaterEq it
            }
        }
        productQuery.categoryId?.let {
            query.adjustWhere {
                ProductTable.categoryId eq it
            }
        }
        productQuery.subCategoryId?.let {
            query.adjustWhere {
                ProductTable.subCategoryId eq it
            }
        }
        productQuery.brandId?.let {
            query.adjustWhere {
                ProductTable.brandId eq it
            }
        }
        query.limit(productQuery.limit).map {
            ProductDAO.Companion.wrapRow(it).response()
        }
    }

    /**
     * Retrieves a product by its ID and user ID.
     *
     * @param userId The ID of the user requesting the product.
     * @param productQuery The filter request containing product details.
     * @return A list of products matching the provided user and filter criteria.
     */
    override suspend fun getProductById(userId: Long, productQuery: ProductWithFilterRequest): List<Product> = query {
        val query = ProductTable.selectAll()

        productQuery.maxPrice?.let {
            query.andWhere { ProductTable.price lessEq it }
        }
        productQuery.maxPrice?.let {
            query.andWhere { ProductTable.price lessEq it }
        }
        productQuery.minPrice?.let {
            query.andWhere {
                ProductTable.price greaterEq it
            }
        }
        productQuery.categoryId.let {
            query.adjustWhere {
                ProductTable.categoryId eq it
            }
        }
        productQuery.subCategoryId.let {
            query.adjustWhere {
                ProductTable.subCategoryId eq it
            }
        }
        productQuery.brandId?.let {
            query.adjustWhere {
                ProductTable.brandId eq it
            }
        }
        query.limit(productQuery.limit).map {
            ProductDAO.Companion.wrapRow(it).response()
        }
    }

    /**
     * Retrieves detailed information for a specific product.
     *
     * @param productId The ID of the product to retrieve.
     * @return The product entity with full details.
     * @throws Exception if the product with the provided ID is not found.
     */
    override suspend fun getProductDetail(productId: Long): Product = query {
        val isProductExist = ProductDAO.Companion.find { ProductTable.id eq productId }.toList().singleOrNull()
        isProductExist?.response() ?: throw productId.notFoundException()
    }

    /**
     * Deletes a product by its ID and user ID.
     *
     * @param userId The ID of the user deleting the product.
     * @param productId The ID of the product to be deleted.
     * @return The ID of the deleted product.
     * @throws Exception if the product with the provided ID is not found.
     */
    override suspend fun deleteProduct(userId: Long, productId: Long): String = query {
        val isProductExist =
            ProductDAO.Companion.find { (ProductTable.id eq productId) }.toList()
                .singleOrNull()
        isProductExist?.let {
            it.delete()
            productId.toString()
        } ?: throw productId.notFoundException()
    }
}