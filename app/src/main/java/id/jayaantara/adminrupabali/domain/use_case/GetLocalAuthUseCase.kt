package id.jayaantara.adminrupabali.domain.use_case

import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.data.repository.AdminRupaBaliRepositoryImpl.Companion.authToken
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetLocalAuthUseCase @Inject constructor(
    private val repository: AdminRupaBaliRepository
){
    operator fun invoke(): Flow<String>{
        return repository.getAuth()
    }
}