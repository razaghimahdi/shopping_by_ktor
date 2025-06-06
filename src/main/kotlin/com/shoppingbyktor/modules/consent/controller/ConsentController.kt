package com.shoppingbyktor.modules.consent.controller

import com.shoppingbyktor.database.entities.PolicyConsentDAO
import com.shoppingbyktor.database.entities.PolicyConsentTable
import com.shoppingbyktor.database.entities.PolicyDocumentDAO
import com.shoppingbyktor.database.entities.PolicyDocumentTable
import com.shoppingbyktor.database.entities.UserDAO
import com.shoppingbyktor.database.entities.UserPolicyConsentResponse
import com.shoppingbyktor.database.models.policy.PolicyConsentRequest
import com.shoppingbyktor.modules.consent.repository.ConsentRepo
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.sql.and
import java.time.LocalDateTime

class ConsentController: ConsentRepo {
    /**
     * Records user consent to a policy
     */
    override suspend fun recordConsent(
        currentUserId: Long,
        consentRequest: PolicyConsentRequest
    ): UserPolicyConsentResponse = query {
        // Verify user and policy exist
        val user = UserDAO.Companion.findById(currentUserId) ?: throw currentUserId.notFoundException()
        val policy =
            PolicyDocumentDAO.Companion.findById(consentRequest.policyId)
                ?: throw consentRequest.policyId.notFoundException()

        // Check if consent already exists, if so update it
        val existingConsent = PolicyConsentDAO.Companion.find {
            PolicyConsentTable.userId eq user.id and (PolicyConsentTable.policyId eq policy.id)
        }.firstOrNull()

        val consent = existingConsent?.// Update existing consent
        apply {
            consentDate = LocalDateTime.now().toString()
            ipAddress = consentRequest.ipAddress
            userAgent = consentRequest.userAgent
        }
            ?: // Create new consent
            PolicyConsentDAO.Companion.new {
                userId = user.id
                policyId = policy
                consentDate = LocalDateTime.now().toString()
                ipAddress = consentRequest.ipAddress
                userAgent = consentRequest.userAgent
            }

        // Return the response
        consent.response()
    }

    /**
     * Gets all consents for a user
     */
    override suspend fun getUserConsents(userId: Long): List<UserPolicyConsentResponse> = query {
        val user = UserDAO.Companion.findById(userId) ?: throw userId.notFoundException()

        PolicyConsentDAO.Companion.find { PolicyConsentTable.userId eq user.id }
            .map { it.response() }
    }

    /**
     * Checks if a user has consented to a specific policy
     */
    override suspend fun hasUserConsented(userId: Long, policyType: PolicyDocumentTable.PolicyType): Boolean = query {
        val user = UserDAO.Companion.findById(userId) ?: throw userId.notFoundException()

        // Find the active policy of the specified type
        val activePolicy = PolicyDocumentDAO.Companion.find {
            PolicyDocumentTable.type eq policyType and (PolicyDocumentTable.isActive eq true)
        }.firstOrNull() ?: return@query false

        // Check if user has consented to this policy
        PolicyConsentDAO.Companion.find {
            PolicyConsentTable.userId eq user.id and (PolicyConsentTable.policyId eq activePolicy.id)
        }.firstOrNull() != null
    }
}