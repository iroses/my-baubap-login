package com.baubap.login.domain.repository

import com.baubap.login.common.Result
import com.baubap.login.domain.model.User

interface LoginRepository {

  suspend fun login(email: String, password: String): Result<User>
}