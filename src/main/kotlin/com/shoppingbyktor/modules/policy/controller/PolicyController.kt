package com.shoppingbyktor.modules.policy.controller

import com.shoppingbyktor.database.entities.PolicyDocumentDAO
import com.shoppingbyktor.database.entities.PolicyDocumentResponse
import com.shoppingbyktor.database.entities.PolicyDocumentTable
import com.shoppingbyktor.database.models.policy.CreatePolicyRequest
import com.shoppingbyktor.database.models.policy.UpdatePolicyRequest
import com.shoppingbyktor.modules.policy.repository.PolicyRepo
import com.shoppingbyktor.utils.CommonException
import com.shoppingbyktor.utils.extension.notFoundException
import com.shoppingbyktor.utils.extension.query
import org.jetbrains.exposed.sql.and

class PolicyController : PolicyRepo {
    /**
     * Creates a new policy document
     */
    override suspend fun createPolicy(createPolicyRequest: CreatePolicyRequest): PolicyDocumentResponse = query {
        // Create a new policy document
        val policyDocument = PolicyDocumentDAO.Companion.new {
            title = createPolicyRequest.title
            type = createPolicyRequest.type
            content = createPolicyRequest.content
            version = createPolicyRequest.version
            effectiveDate = createPolicyRequest.effectiveDate
        }

        // If this is a new active policy of the same type, deactivate previous versions
        if (policyDocument.isActive) {
            PolicyDocumentDAO.Companion.find {
                PolicyDocumentTable.type eq createPolicyRequest.type and
                        (PolicyDocumentTable.id neq policyDocument.id) and
                        (PolicyDocumentTable.isActive eq true)
            }.forEach { it.isActive = false }
        }

        policyDocument.response()
    }

    /**
     * Updates an existing policy document
     */
    override suspend fun updatePolicy(id: Long, updatePolicyRequest: UpdatePolicyRequest): PolicyDocumentResponse =
        query {
            val policyDocument = PolicyDocumentDAO.Companion.findById(id) ?: throw id.notFoundException()

            // Update only the fields that are provided
            updatePolicyRequest.title?.let { policyDocument.title = it }
            updatePolicyRequest.content?.let { policyDocument.content = it }
            updatePolicyRequest.version?.let { policyDocument.version = it }
            updatePolicyRequest.effectiveDate?.let { policyDocument.effectiveDate = it }
            updatePolicyRequest.isActive?.let {
                policyDocument.isActive = it

                // If setting this policy to active, deactivate other policies of the same type
                if (it) {
                    PolicyDocumentDAO.Companion.find {
                        PolicyDocumentTable.type eq policyDocument.type and
                                (PolicyDocumentTable.id neq policyDocument.id) and
                                (PolicyDocumentTable.isActive eq true)
                    }.forEach { otherPolicy -> otherPolicy.isActive = false }
                }
            }
            policyDocument.response()
        }

    /**
     * Gets a policy document by type, returning the latest active version
     */
    override suspend fun getPolicyByType(type: PolicyDocumentTable.PolicyType): PolicyDocumentResponse = query {
        val policyDocument = PolicyDocumentDAO.Companion.find {
            PolicyDocumentTable.type eq type and (PolicyDocumentTable.isActive eq true)
        }.firstOrNull() ?: throw CommonException("No active $type found")

        policyDocument.response()
    }

    /**
     * Gets a policy document by ID
     */
    override suspend fun getPolicyById(id: Long): PolicyDocumentResponse = query {
        val policyDocument = PolicyDocumentDAO.Companion.findById(id) ?: throw id.notFoundException()
        policyDocument.response()
    }

    /**
     * Gets all policy documents, optionally filtered by type
     */
    override suspend fun getAllPolicies(type: PolicyDocumentTable.PolicyType?): List<PolicyDocumentResponse> = query {
        val query = if (type != null) {
            PolicyDocumentDAO.Companion.find { PolicyDocumentTable.type eq type }
        } else {
            PolicyDocumentDAO.Companion.all()
        }

        query.map { it.response() }
    }

    /**
     * Deactivates a policy document (doesn't delete, just marks as inactive)
     */
    override suspend fun deactivatePolicy(id: Long): Boolean = query {
        val policyDocument = PolicyDocumentDAO.Companion.findById(id) ?: throw id.notFoundException()
        policyDocument.isActive = false
        true
    }
}