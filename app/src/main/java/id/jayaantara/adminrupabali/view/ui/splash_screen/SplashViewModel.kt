package id.jayaantara.adminrupabali.view.ui.splash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jayaantara.adminrupabali.domain.use_case.GetLocalAuthUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLocalAuthUseCase: GetLocalAuthUseCase
): ViewModel(){

    private val _localAuthState = MutableLiveData<String>()
    val localAuthState: LiveData<String> get() = _localAuthState

    init {
        getLocalAuth()
    }

    private fun getLocalAuth() = viewModelScope.launch {
        getLocalAuthUseCase().collect { values ->
            _localAuthState.value = values
        }
    }
}