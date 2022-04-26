package com.baubap.login.data.repository

import com.baubap.login.common.Result
import com.baubap.login.domain.model.User
import com.baubap.login.domain.repository.LoginRepository
import javax.inject.Inject

class FakeLoginRepositoryImpl @Inject constructor() : LoginRepository {

  private var shouldReturnError = false

  fun setReturnError(value: Boolean) {
    shouldReturnError = value
  }

  override suspend fun login(email: String, password: String): Result<User> {
    if (shouldReturnError) {
      return Result.Error("Unknown error message.")
    }
    return Result.Success(User(name = "Rick Sanchez", email = "rick_sanchez@gmail.com"))
  }
}