package id.jayaantara.adminrupabali.data.remote.dto

import com.google.gson.annotations.SerializedName
import id.jayaantara.adminrupabali.common.AdminRole
import java.util.*

data class AdminDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("fullname")
    val fullname: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("profilePicture")
    val profilePicture: String,

    @SerializedName("isVerify")
    val isVerify: Boolean,

    @SerializedName("identityCard")
    val identityCard: String,

    @SerializedName("provingDocument")
    val provingDocument: String,

    @SerializedName("createdAt")
    val createdAt: Date,

    @SerializedName("updatedAt")
    val updatedAt: Date,
)
