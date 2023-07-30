package id.jayaantara.adminrupabali.view.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jayaantara.adminrupabali.common.AdminRole
import id.jayaantara.adminrupabali.common.Resource
import id.jayaantara.adminrupabali.domain.model.AdminAuth
import id.jayaantara.adminrupabali.domain.model.Response
import id.jayaantara.adminrupabali.domain.use_case.CheckEmailUseCase
import id.jayaantara.adminrupabali.domain.use_case.LoginUseCase
import id.jayaantara.adminrupabali.domain.use_case.RegisterAdminUseCase
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
    private val registerAdminUseCase: RegisterAdminUseCase
) : ViewModel(){

    private val _registerState = MutableLiveData<Resource<Response>>()
    val registerState: LiveData<Resource<Response>> get() = _registerState

    private val _checkEmailState = MutableLiveData<Resource<Boolean>>()
    val checkEmailState: LiveData<Resource<Boolean>> get() = _checkEmailState

    private val _adminAuthState = MutableLiveData<Resource<AdminAuth>>()
    val adminAuthState: LiveData<Resource<AdminAuth>> get() = _adminAuthState

    fun login(email: String, password: String) = viewModelScope.launch {
        loginUseCase(email, password).collect { values ->
            _adminAuthState.value = values
        }
    }

    fun resetRegisterState() = viewModelScope.launch{
        _registerState.value = null
    }

    fun resetCheckEmailState() = viewModelScope.launch{
        _checkEmailState.value = null
    }

    fun isEmailExist(email: String) = viewModelScope.launch {
        checkEmailUseCase(email = email).collect{ values ->
            _checkEmailState.value = values
        }
    }

    fun registerAdmin(
        identityCard: File,
        provingDocument: File,
        fullname: String,
        nickname: String,
        email: String,
        password: String,
        role: String
    ) = viewModelScope.launch {
        registerAdminUseCase(
            identityCard = identityCard,
            provingDocument = provingDocument,
            fullname = fullname,
            nickname = nickname,
            email = email,
            password = password,
            role = role
        ).collect { values ->
            _registerState.value = values
        }
    }
}