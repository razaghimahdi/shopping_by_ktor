package com.shoppingbyktor.modules.brand.controller

import com.shoppingbyktor.database.entities.Brand
import com.shoppingbyktor.database.entities.BrandDAO
import com.shoppingbyktor.database.entities.BrandTable
import com.shoppingbyktor.modules.brand.repository.BrandRepo
import com.shoppingbyktor.utils.extension.alreadyExistException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query

/**
 * Controller for managing brand-related operations.
 */
class BrandController : BrandRepo {
    /**
     * Creates a new brand if it does not already exist.
     *
     * @param name The name of the brand to be created.
     * @return The created brand entity.
     * @throws Exception if the brand name already exists.
     */
    override suspend fun createBrand(name: String): Brand = query {
        val isBrandExist = BrandDAO.Companion.find { BrandTable.name eq name }.toList().singleOrNull()
        isBrandExist?.let {
            throw name.alreadyExistException()
        } ?: BrandDAO.Companion.new {
            this.name = name
        }.response()
    }

    /**
     * Retrieves a list of brands with a specified limit.
     *
     * @param limit The maximum number of brands to retrieve.
     * @return A list of brand entities.
     */
    override suspend fun getBrands(limit: Int): List<Brand> = query {
        BrandDAO.Companion.all().limit(limit).map {
            it.response()
        }
    }

    /**
     * Updates the name of an existing brand.
     *
     * @param brandId The ID of the brand to update.
     * @param name The new name for the brand.
     * @return The updated brand entity.
     * @throws Exception if the brand ID is not found.
     */
    override suspend fun updateBrand(brandId: Long, name: String): Brand = query {
        val isBrandExist = BrandDAO.Companion.find { BrandTable.id eq brandId }.toList().singleOrNull()
        isBrandExist?.let {
            it.name = name
            it.response()
        } ?: throw brandId.notFoundException()
    }

    /**
     * Deletes a brand by its ID.
     *
     * @param brandId The ID of the brand to delete.
     * @return The ID of the deleted brand.
     * @throws Exception if the brand ID is not found.
     */
    override suspend fun deleteBrand(brandId: Long): String = query {
        val isBrandExist = BrandDAO.Companion.find { BrandTable.id eq brandId }.toList().singleOrNull()
        isBrandExist?.let {
            it.delete()
            brandId.toString()
        } ?: throw brandId.notFoundException()
    }
}