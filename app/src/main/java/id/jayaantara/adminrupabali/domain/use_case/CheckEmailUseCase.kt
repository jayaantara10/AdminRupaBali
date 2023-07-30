package id.jayaantara.adminrupabali.domain.use_case

import android.util.Log
import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.domain.model.Response
import id.jayaantara.adminrupabali.domain.model.toResponse
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor(
    private val repository: AdminRupaBaliRepository
){
    operator fun invoke(email: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val isEmailExist = repository.isEmailExist(email = email)
            emit(Resource.Success(isEmailExist))
        } catch (error: HttpException){
            error.message?.let { Log.e("Check Email", it) }
            emit(Resource.Error(errorCode = error.code()))
        } catch (error: IOException) {
            error.message?.let { Log.e("Check Email", it) }
            emit(Resource.Error(errorCode = 400))
        }
    }
}