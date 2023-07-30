package id.jayaantara.adminrupabali.domain.use_case

import android.util.Log
import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.domain.model.Response
import id.jayaantara.adminrupabali.domain.model.toResponse
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UpdateProfilePictureAdminUseCase @Inject constructor(
    private val repository: AdminRupaBaliRepository
)  {
    operator fun invoke(
        token: String,
        profilePicture: File
    ): Flow<Resource<Response>> = flow{
        try {
            emit(Resource.Loading())
            val response = repository.updateProfilePictureAdmin(
                token = token,
                profilePicture = profilePicture
            ).toResponse()
            emit(Resource.Success(response))
        } catch (error: HttpException){
            error.message?.let { Log.e("Update Profile Picture", it) }
            emit(Resource.Error(errorCode = error.code()))
        } catch (error: IOException) {
            error.message?.let { Log.e("Update Profile Picture", it) }
            emit(Resource.Error(errorCode = 400))
        }
    }
}