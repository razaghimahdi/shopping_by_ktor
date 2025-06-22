package com.shoppingbyktor.modules.address.repository

import com.shoppingbyktor.database.entities.Address
import com.shoppingbyktor.database.entities.Cart
import com.shoppingbyktor.database.entities.Product

interface AddressRepo {

    /**
     * Creates a new address for a user.
     *
     * @param userId The unique identifier of the user.
     * @param address The full address of the user.
     * @param country The country where the address is located.
     * @param city The city of the address.
     * @param state The state or province of the address.
     * @param zipCode The postal or zip code of the address.
     * @param lan The longitude of the address location.
     * @param lat The latitude of the address location.
     * @return The newly created address object or any related result.
     */
    suspend fun createAddress(
        userId: Long,
        address: String,
        country: String,
        city: String,
        state: String,
        zipCode: String,
        lan: String,
        lat: String,
    ): Any?

    /**
     * Retrieves all saved addresses for a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return A list of the user's addresses.
     */
    suspend fun getAddresses(userId: Long): List<Address>

    /**
     * Updates an existing address for a user.
     *
     * @param userId The unique identifier of the user.
     * @param addressId The unique identifier of the address to be updated.
     * @param address The new full address.
     * @param country The updated country.
     * @param city The updated city.
     * @param state The updated state or province.
     * @param zipCode The updated postal or zip code.
     * @param lan The updated longitude.
     * @param lat The updated latitude.
     * @return The updated address.
     */
    suspend fun updateAddress(
        userId: Long,
        addressId: Long,
        address: String,
        country: String,
        city: String,
        state: String,
        zipCode: String,
        lan: String,
        lat: String,
    ): Address

    /**
     * Removes a specific address for a user.
     *
     * @param userId The unique identifier of the user.
     * @param addressId The unique identifier of the address to be removed.
     * @return The removed address object or any related result.
     */
    suspend fun removeAddress(
        userId: Long,
        addressId: Long,
    ): Any?
}
