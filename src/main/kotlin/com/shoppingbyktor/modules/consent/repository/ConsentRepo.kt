package com.shoppingbyktor.modules.consent.repository

import com.shoppingbyktor.database.entities.PolicyDocumentTable
import com.shoppingbyktor.database.entities.UserPolicyConsentResponse
import com.shoppingbyktor.database.models.policy.PolicyConsentRequest

interface ConsentRepo {
    /**
     * Records user consent to a policy
     */
    suspend fun recordConsent(userId: Long, consentRequest: PolicyConsentRequest): UserPolicyConsentResponse

    /**
     * Gets all consents for a user
     */
    suspend fun getUserConsents(userId: Long): List<UserPolicyConsentResponse>

    /**
     * Checks if a user has consented to a specific policy
     */
    suspend fun hasUserConsented(userId: Long, policyType: PolicyDocumentTable.PolicyType): Boolean
}