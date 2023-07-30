package id.jayaantara.adminrupabali.domain.repository

import id.jayaantara.adminrupabali.common.AdminRole
import id.jayaantara.adminrupabali.data.remote.dto.AdminAuthDto
import id.jayaantara.adminrupabali.data.remote.dto.AdminDto
import id.jayaantara.adminrupabali.data.remote.dto.ResponseDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AdminRupaBaliRepository {

    suspend fun login(
        email: String,
        password: String
    ): AdminAuthDto

    suspend fun updateAuth(token: String)

    fun getAuth(): Flow<String>

    suspend fun deleteAuth()

    suspend fun getAdminProfile(token: String): AdminDto

    suspend fun isEmailExist(email: String): Boolean

    suspend fun registerAdmin(
        identityCard: File,
        provingDocument: File,
        fullname: String,
        nickname: String,
        email: String,
        password: String,
        role: String
    ): ResponseDto

    suspend fun updateProfilePictureAdmin(
        token: String,
        profilePicture: File,
    ): ResponseDto

    suspend fun updateProfileAdmin(
        token: String,
        nickname: String,
    ): ResponseDto
}