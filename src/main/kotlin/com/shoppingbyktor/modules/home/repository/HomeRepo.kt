package com.shoppingbyktor.modules.home.repository

import com.shoppingbyktor.database.models.home.response.HomeResponse

interface HomeRepo {
    /**
     * get home detail.
     *
     *
     * @return The home response.
     */
    suspend fun home(): HomeResponse
}