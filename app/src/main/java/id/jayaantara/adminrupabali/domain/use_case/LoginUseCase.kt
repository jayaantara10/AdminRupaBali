package id.jayaantara.adminrupabali.domain.use_case

import android.util.Log
import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.domain.model.AdminAuth
import id.jayaantara.adminrupabali.domain.model.toAdminAuth
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AdminRupaBaliRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<AdminAuth>> = flow {
        try {
            emit(Resource.Loading())
            val adminAuth = repository.login(email, password).toAdminAuth()
            repository.updateAuth(adminAuth.token)
            emit(Resource.Success(adminAuth))
        } catch (error: HttpException){
            error.message?.let { Log.e("Login", it) }
            emit(Resource.Error(errorCode = error.code()))
        } catch (error: IOException) {
            error.message?.let { Log.e("Login", it) }
            emit(Resource.Error(errorCode = 400))
        }
    }
}