package id.jayaantara.adminrupabali.domain.model

import com.google.gson.annotations.SerializedName
import id.jayaantara.adminrupabali.common.AdminRole
import id.jayaantara.adminrupabali.data.remote.dto.AdminAuthDto
import id.jayaantara.adminrupabali.data.remote.dto.AdminDto
import java.util.*

data class Admin(
    val id: String,

    val nickname: String,

    val fullname: String,

    val email: String,

    val role: AdminRole,

    val profilePicture: String,

    val isVerify: Boolean,

    val identityCard: String,

    val provingDocument: String,

    val createdAt: Date,

    val updatedAt: Date,
)

fun AdminDto.toAdmin(): Admin {
    return Admin(
        id = id,
        nickname = nickname,
        fullname = fullname,
        email = email,
        role = convertRole(role),
        profilePicture = profilePicture,
        isVerify = isVerify,
        identityCard = identityCard,
        provingDocument = provingDocument,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun AdminDto.convertRole(role: String): AdminRole {
    return when (role) {
        AdminRole.SUPER_ADMIN.role -> AdminRole.SUPER_ADMIN
        AdminRole.ADMIN.role ->  AdminRole.ADMIN
        AdminRole.VALIDATOR.role ->  AdminRole.VALIDATOR
        else -> AdminRole.UNDEFINE
    }
}

//val dummyAdmin = Admin(
//        id = "id",
//        nickname = "nickname",
//        fullname = "fullname",
//        email = "email",
//        role = AdminRole.UNDEFINE,
//        profilePicture = "profilePicture",
//        isVerify = false,
//        identityCard = "identityCard",
//        provingDocument = "provingDocument",
//        createdAt = Date("2023-04-21 12:50:18.333536"),
//        updatedAt = Date("2023-04-21 12:50:18.333536")
//)

