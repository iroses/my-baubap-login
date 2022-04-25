package com.baubap.login.data.remote.dto

import com.baubap.login.domain.model.User

data class LoginResponse(
  val id: Int,
  val firstName: String,
  val lastName: String,
  val email: String
)

fun LoginResponse.toUser(): User {
  return User(name = "$firstName $lastName", email = email)
}