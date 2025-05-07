package com.shoppingbyktor.database.models.user.body

import com.shoppingbyktor.plugins.RoleManagement
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isIn
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class RegisterRequest(val name: String, val email: String, val password: String) {
    fun validation() {
        validate(this) {
            validate(RegisterRequest::email).isNotNull().isEmail()
            validate(RegisterRequest::password).isNotNull().hasSize(4, 15)
            validate(RegisterRequest::name).isNotNull()
                .isIn(RoleManagement.CUSTOMER.role)
        }
    }
}