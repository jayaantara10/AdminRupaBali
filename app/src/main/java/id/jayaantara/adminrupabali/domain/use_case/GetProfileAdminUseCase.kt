package id.jayaantara.adminrupabali.domain.use_case

import android.util.Log
import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.domain.model.Admin
import id.jayaantara.adminrupabali.domain.model.AdminAuth
import id.jayaantara.adminrupabali.domain.model.toAdmin
import id.jayaantara.adminrupabali.domain.model.toAdminAuth
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProfileAdminUseCase @Inject constructor(
    private val repository: AdminRupaBaliRepository
){
    operator fun invoke(token: String): Flow<Resource<Admin>> = flow {
        try {
            emit(Resource.Loading())
            val admin = repository.getAdminProfile(token = token).toAdmin()
            emit(Resource.Success(admin))
        } catch (error: HttpException){
            error.message?.let { Log.e("Get Profile Admin", it) }
            emit(Resource.Error(errorCode = error.code()))
        } catch (error: IOException) {
            error.message?.let { Log.e("Get Profile Admin", it) }
            emit(Resource.Error(errorCode = 400))
        }
    }
}