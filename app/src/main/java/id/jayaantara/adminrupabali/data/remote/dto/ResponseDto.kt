package id.jayaantara.adminrupabali.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("message")
    val message: String,
)
