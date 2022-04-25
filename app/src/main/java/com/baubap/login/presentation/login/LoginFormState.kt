package com.baubap.login.presentation.login

sealed class LoginFormState {

  data class EmailError(val error: Int) : LoginFormState()

  data class PasswordError(val error: Int) : LoginFormState()
}
