package id.jayaantara.adminrupabali.domain.use_case

import id.jayaantara.adminrupabali.data.repository.AdminRupaBaliRepositoryImpl
import id.jayaantara.adminrupabali.domain.repository.AdminRupaBaliRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AdminRupaBaliRepository
) {
    suspend operator fun invoke(){
        repository.deleteAuth()
    }
}