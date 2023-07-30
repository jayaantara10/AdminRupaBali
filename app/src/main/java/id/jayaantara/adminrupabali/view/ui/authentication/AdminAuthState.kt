package id.jayaantara.adminrupabali.view.ui.authentication

import id.jayaantara.adminrupabali.domain.model.AdminAuth

data class AdminAuthState(
    val isLoading: Boolean = false,
    val adminAuth: AdminAuth? = null,
    val error: Int = 0
)
