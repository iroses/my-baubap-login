package com.baubap.login.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.baubap.login.MainCoroutineRule
import com.baubap.login.common.Result
import com.baubap.login.domain.model.User
import com.baubap.login.domain.repository.LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

  @ExperimentalCoroutinesApi
  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  var loginRepository: LoginRepository = mock()

  private lateinit var loginUseCase: LoginUseCase

  @Before
  fun setUp() {
    loginUseCase = LoginUseCase(loginRepository)
  }

  @Test
  fun login_success() = runTest {
    val email = "rick_sanchez@gmail.com"
    val password = "Password123"

    whenever(loginRepository.login(any(), any()))
      .thenReturn(Result.Success(User(name = "Rick Sanchez", email = "rick_sanchez@gmail.com")))

    val response = loginUseCase(email, password)

    assertThat(response is Result.Success, `is`(true))
    assertThat((response as Result.Success).data.name, `is`("Rick Sanchez"))
    assertThat(response.data.email, `is`("rick_sanchez@gmail.com"))
  }

  @Test
  fun login_error() = runTest {
    val email = "rick_sanchez@gmail.com"
    val password = "Password123"

    whenever(loginRepository.login(any(), any()))
      .thenReturn(Result.Error("Unknown error message."))

    val response = loginUseCase(email, password)

    assertThat(response is Result.Error, `is`(true))
    assertThat((response as Result.Error).message, `is`("Unknown error message."))
  }
}