package com.shoppingbyktor.database.models.policy

import com.shoppingbyktor.database.entities.PolicyDocumentTable
import kotlinx.serialization.Serializable

/**
 * Request model for creating a policy document
 */
@Serializable
data class CreatePolicyRequest(
    val title: String,
    val type: PolicyDocumentTable.PolicyType,
    val content: String,
    val version: String,
    val effectiveDate: String
)

/**
 * Request model for updating a policy document
 */
@Serializable
data class UpdatePolicyRequest(
    val title: String? = null,
    val content: String? = null,
    val version: String? = null,
    val effectiveDate: String? = null,
    val isActive: Boolean? = null
)

/**
 * Request model for recording user consent to a policy
 */
@Serializable
data class PolicyConsentRequest(
    val policyId: Long,
    val ipAddress: String? = null,
    val userAgent: String? = null
)