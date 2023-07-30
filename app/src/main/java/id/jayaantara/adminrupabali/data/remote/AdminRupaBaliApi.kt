package id.jayaantara.adminrupabali.data.remote

import com.google.gson.annotations.JsonAdapter
import id.jayaantara.adminrupabali.data.remote.dto.AdminAuthDto
import id.jayaantara.adminrupabali.data.remote.dto.AdminDto
import id.jayaantara.adminrupabali.data.remote.dto.ResponseDto
import id.jayaantara.adminrupabali.domain.model.AdminAuth
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AdminRupaBaliApi {

    @FormUrlEncoded
    @POST("auth/login/admin")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): AdminAuthDto

    @GET("admin/profile")
    suspend fun getAdminProfile(
        @Header("Authorization") token: String
    ): AdminDto

    @GET("admin/check-email/{email}")
    suspend fun isEmailExist(
        @Path("email") email: String,
    ): Boolean

    @Multipart
    @POST("admin")
    suspend fun registerAdmin(
        @Part identityCard: MultipartBody.Part,
        @Part provingDocument: MultipartBody.Part,
        @Part("fullname") fullname: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("role") role: RequestBody,
    ): ResponseDto

    @Multipart
    @PUT("admin/update-profile-picture")
    suspend fun updateProfilePictureAdmin(
        @Header("Authorization") token: String,
        @Part profilePicture: MultipartBody.Part,
    ): ResponseDto

    @FormUrlEncoded
    @PUT("admin/update-profile")
    suspend fun updateProfileAdmin(
        @Header("Authorization") token: String,
        @Field("nickname") nickname: String,
    ): ResponseDto

    @FormUrlEncoded
    @PUT("admin/update-password")
    suspend fun selfUpdatePasswordAdmin(
        @Header("Authorization") token: String,
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String,
    ): ResponseDto

}