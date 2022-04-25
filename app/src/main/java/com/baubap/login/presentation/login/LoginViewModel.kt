package com.baubap.login.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baubap.login.R
import com.baubap.login.common.Result
import com.baubap.login.domain.usecases.LoginUseCase
import com.baubap.login.presentation.login.LoginFormState.EmailError
import com.baubap.login.presentation.login.LoginFormState.PasswordError
import com.baubap.login.utils.isInvalidEmail
import com.baubap.login.utils.isInvalidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase
) : ViewModel() {

  private val _loginFormState = MutableLiveData<LoginFormState>()
  val loginFormState: LiveData<LoginFormState> = _loginFormState

  private val _loginState = MutableLiveData<LoginState>()
  val loginState: LiveData<LoginState> = _loginState

  fun attemptLogin(email: String, password: String) {
    when {
      email.isInvalidEmail() -> {
        _loginFormState.value = EmailError(error = R.string.error_invalid_username)
      }
      password.isInvalidPassword() -> {
        _loginFormState.value = PasswordError(error = R.string.error_invalid_password)
      }
      else -> {
        login(email, password)
      }
    }
  }

  fun login(email: String, password: String) {
    viewModelScope.launch {
      _loginState.postValue(LoginState.Loading)
      when (val response = loginUseCase(email, password)) {
        is Result.Success -> _loginState.postValue((LoginState.Success(user = response.data)))
        is Result.Error -> _loginState.postValue(LoginState.Error(error = R.string.error_login_failed))
      }
    }
  }
}