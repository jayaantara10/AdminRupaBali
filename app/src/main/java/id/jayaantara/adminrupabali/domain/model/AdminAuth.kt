package id.jayaantara.adminrupabali.domain.model

import id.jayaantara.adminrupabali.data.remote.dto.AdminAuthDto

data class AdminAuth(
    val token: String
)

fun AdminAuthDto.toAdminAuth(): AdminAuth {
    return AdminAuth(
        token = token
    )
}