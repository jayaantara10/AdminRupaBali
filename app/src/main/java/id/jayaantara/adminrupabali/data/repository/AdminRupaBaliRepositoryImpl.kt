package id.jayaantara.adminrupabali.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import id.jayaantara.adminrupabali.common.AdminRole
import id.jayaantara.adminrupabali.data.remote.AdminRupaBaliApi
import id.jayaantara.adminrupabali.data.remote.dto.AdminAuthDto
import id.jayaantara.adminrupabali.data.remote.dto.AdminDto
import id.jayaantara.adminrupabali.data.remote.dto.ResponseDto
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class AdminRupaBaliRepositoryImpl @Inject constructor(
    private val api: AdminRupaBaliApi,
    private val dataStore: DataStore<Preferences>
) : AdminRupaBaliRepository {

    // AUTHENTICATION
    //Login
    override suspend fun login(email: String, password: String): AdminAuthDto {
        return api.login(email = email, password = password)
    }

    // Save Auth in local data store
    override suspend fun updateAuth(token: String) {
        dataStore.edit { preference ->
            preference[authToken] = token
        }
    }

    // Get Auth from local datastore
    override fun getAuth(): Flow<String> {
        val authFlow = dataStore.data.map { preference ->
            preference[authToken] ?: ""
        }
        return authFlow
    }

    // Delete Auth in local data store
    override suspend fun deleteAuth() {
        dataStore.edit { preference ->
            preference.clear()
        }
    }

    // ============================================================================================

    //MANAGEMENT ADMIN
    // Check Email
    override suspend fun isEmailExist(email: String): Boolean {
        return api.isEmailExist(email = email)
    }

    // Register Admin
    override suspend fun registerAdmin(
        identityCard: File,
        provingDocument: File,
        fullname: String,
        nickname: String,
        email: String,
        password: String,
        role: String
    ): ResponseDto {
        val reqIdentityCard = identityCard.asRequestBody("image/jpeg".toMediaType())
        val identityCardMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "identityCard",
            identityCard.name,
            reqIdentityCard
        )

        val reqProvingDocument = provingDocument.asRequestBody("application/pdf".toMediaType())
        val provingDocumentMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "provingDocument",
            provingDocument.name,
            reqProvingDocument
        )

        val reqFullname = fullname.toRequestBody("text/plain".toMediaType())
        val reqNickname = nickname.toRequestBody("text/plain".toMediaType())
        val reqEmail = email.toRequestBody("text/plain".toMediaType())
        val reqPassword = password.toRequestBody("text/plain".toMediaType())
        val reqRole = role.toRequestBody("text/plain".toMediaType())

        return api.registerAdmin(
            identityCard = identityCardMultipart,
            provingDocument = provingDocumentMultipart,
            fullname = reqFullname,
            nickname = reqNickname,
            email = reqEmail,
            password = reqPassword,
            role = reqRole
        )
    }

    override suspend fun updateProfilePictureAdmin(
        token: String,
        profilePicture: File
    ): ResponseDto {
        val reqProfilePicture = profilePicture.asRequestBody("image/jpeg".toMediaType())
        val profilePictureMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "profilePicture",
            profilePicture.name,
            reqProfilePicture
        )

        return api.updateProfilePictureAdmin(
            token = "Bearer $token",
            profilePicture = profilePictureMultipart
        )
    }

    override suspend fun updateProfileAdmin(token: String, nickname: String): ResponseDto {
        return api.updateProfileAdmin(
            token = "Bearer $token",
            nickname = nickname
        )
    }


    // Get Admin Profile
    override suspend fun getAdminProfile(token: String): AdminDto {
        return api.getAdminProfile(token = "Bearer $token")
    }

    companion object {
        private const val AUTH_TOKEN = "auth_token"
        val authToken = stringPreferencesKey(AUTH_TOKEN)
    }

}