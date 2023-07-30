package id.jayaantara.adminrupabali.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AdminAuthDto(
    @SerializedName("token")
    val token: String
)
