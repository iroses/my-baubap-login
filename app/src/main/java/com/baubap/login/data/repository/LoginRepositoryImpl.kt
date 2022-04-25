package com.baubap.login.data.repository

import com.baubap.login.common.Result
import com.baubap.login.data.remote.LoginApi
import com.baubap.login.data.remote.dto.toUser
import com.baubap.login.domain.model.User
import com.baubap.login.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {

  override suspend fun login(email: String, password: String): Result<User> =
    withContext(Dispatchers.IO) {
      delay(1000)
      return@withContext try {
        val response = LoginApi.login(email, password)
        Result.Success((response as Result.Success).data.toUser())
      } catch (e: Exception) {
        Result.Error(e.localizedMessage ?: "Unknown error message.")
      }
    }
}