package com.baubap.login.data.remote

import com.baubap.login.common.Result
import com.baubap.login.data.remote.dto.LoginResponse

object LoginApi {

  private val users = mutableMapOf(
    "rick_sanchez@gmail.com" to "Password123"
  )

  fun login(email: String, password: String): Result<LoginResponse> {
    users[email]?.let {
      if (it == password) {
        return Result.Success(
          LoginResponse(
            id = 1,
            firstName = "Rick",
            lastName = "Sanchez",
            email = "rick_sanchez@gmail.com"
          )
        )
      }
    }
    return Result.Error("Invalid email or password")
  }
}