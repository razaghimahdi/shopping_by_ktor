package com.shoppingbyktor.modules.policy.repository

import com.shoppingbyktor.database.entities.PolicyDocumentResponse
import com.shoppingbyktor.database.entities.PolicyDocumentTable
import com.shoppingbyktor.database.models.policy.CreatePolicyRequest
import com.shoppingbyktor.database.models.policy.UpdatePolicyRequest

interface PolicyRepo {
    /**
     * Creates a new policy document
     */
    suspend fun createPolicy(createPolicyRequest: CreatePolicyRequest): PolicyDocumentResponse

    /**
     * Updates an existing policy document
     */
    suspend fun updatePolicy(id: Long, updatePolicyRequest: UpdatePolicyRequest): PolicyDocumentResponse

    /**
     * Gets a policy document by type, returning the latest active version
     */
    suspend fun getPolicyByType(type: PolicyDocumentTable.PolicyType): PolicyDocumentResponse

    /**
     * Gets a policy document by ID
     */
    suspend fun getPolicyById(id: Long): PolicyDocumentResponse

    /**
     * Gets all policy documents, optionally filtered by type
     */
    suspend fun getAllPolicies(type: PolicyDocumentTable.PolicyType? = null): List<PolicyDocumentResponse>

    /**
     * Deactivates a policy document (doesn't delete, just marks as inactive)
     */
    suspend fun deactivatePolicy(id: Long): Boolean
}