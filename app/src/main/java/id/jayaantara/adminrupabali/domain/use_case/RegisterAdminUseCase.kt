package id.jayaantara.adminrupabali.domain.use_case

import android.util.Log
import id.jayaantara.adminrupabali.common.AdminRole
import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.domain.model.AdminAuth
import id.jayaantara.adminrupabali.domain.model.Response
import id.jayaantara.adminrupabali.domain.model.toAdminAuth
import id.jayaantara.adminrupabali.domain.model.toResponse
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class RegisterAdminUseCase @Inject constructor(
    private val repository: AdminRupaBaliRepository
) {
    operator fun invoke(
        identityCard: File,
        provingDocument: File,
        fullname: String,
        nickname: String,
        email: String,
        password: String,
        role: String
    ): Flow<Resource<Response>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.registerAdmin(
                identityCard = identityCard,
                provingDocument = provingDocument,
                fullname = fullname,
                nickname = nickname,
                email = email,
                password = password,
                role = role
            ).toResponse()
            emit(Resource.Success(response))
        } catch (error: HttpException){
            error.message?.let { Log.e("Register Admin", it) }
            emit(Resource.Error(errorCode = error.code()))
        } catch (error: IOException) {
            error.message?.let { Log.e("Register Admin", it) }
            emit(Resource.Error(errorCode = 400))
        }
    }
}