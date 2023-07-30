package id.jayaantara.adminrupabali.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.domain.model.Admin
import id.jayaantara.adminrupabali.domain.model.Response
import id.jayaantara.adminrupabali.domain.use_case.GetLocalAuthUseCase
import id.jayaantara.adminrupabali.domain.use_case.GetProfileAdminUseCase
import id.jayaantara.adminrupabali.domain.use_case.LoginUseCase
import id.jayaantara.adminrupabali.domain.use_case.LogoutUseCase
import id.jayaantara.adminrupabali.domain.use_case.UpdateProfileAdminUseCase
import id.jayaantara.adminrupabali.domain.use_case.UpdateProfilePictureAdminUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocalAuthUseCase: GetLocalAuthUseCase,
    private val getProfileAdminUseCase: GetProfileAdminUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateProfilePictureAdminUseCase: UpdateProfilePictureAdminUseCase,
    private val updateProfileAdminUseCase: UpdateProfileAdminUseCase
) : ViewModel(){

    private val _localAuthState = MutableLiveData<String>()
    val localAuthState: LiveData<String> get() = _localAuthState

    private val _adminState = MutableLiveData<Resource<Admin>>()
    val adminState: LiveData<Resource<Admin>> get() = _adminState

    private val _updateProfilePictureAdminState = MutableLiveData<Resource<Response>>()
    val updateProfilePictureAdminState: LiveData<Resource<Response>> get() = _updateProfilePictureAdminState

    private val _updateProfileAdminState = MutableLiveData<Resource<Response>>()
    val updateProfileAdminState: LiveData<Resource<Response>> get() = _updateProfileAdminState

    init {
        getLocalAuth()
        localAuthState.value?.let { getProfileAdmin() }
    }

    private fun getLocalAuth() = viewModelScope.launch {
        getLocalAuthUseCase().collect { values ->
            _localAuthState.value = values
        }
    }

    fun getProfileAdmin() = viewModelScope.launch {
        localAuthState.value?.let {
            getProfileAdminUseCase(token = it).collect { values ->
                _adminState.value = values
            }
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase()
    }

    fun resetUpdateProfilePictureState() = viewModelScope.launch{
        _updateProfilePictureAdminState.value = null
    }

    fun updateProfilePictureAdmin(
        profilePicture: File,
    ) = viewModelScope.launch {
        localAuthState.value?.let {
            updateProfilePictureAdminUseCase(
                token = it,
                profilePicture = profilePicture
            ).collect { values ->
                _updateProfilePictureAdminState.value = values
            }
        }
    }

    fun resetUpdateProfileState() = viewModelScope.launch{
        _updateProfileAdminState.value = null
    }

    fun updateProfileAdmin(
        nickname: String,
    ) = viewModelScope.launch {
        localAuthState.value?.let {
            updateProfileAdminUseCase(
                token = it,
                nickname = nickname
            ).collect { values ->
                _updateProfileAdminState.value = values
            }
        }
    }
}