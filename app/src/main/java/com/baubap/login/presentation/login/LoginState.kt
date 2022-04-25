package com.baubap.login.presentation.login

import com.baubap.login.domain.model.User

sealed class LoginState {

  object Loading : LoginState()

  data class Success(val user: User) : LoginState()

  data class Error(val error: Int) : LoginState()
}
