package com.baubap.login.domain.usecases

import com.baubap.login.common.Result
import com.baubap.login.domain.model.User
import com.baubap.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
  private val loginRepository: LoginRepository
) {

  suspend operator fun invoke(email: String, password: String): Result<User> {
    return loginRepository.login(email, password)
  }
}