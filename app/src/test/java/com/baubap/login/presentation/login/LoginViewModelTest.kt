package com.baubap.login.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.baubap.login.MainCoroutineRule
import com.baubap.login.common.Result
import com.baubap.login.domain.model.User
import com.baubap.login.domain.usecases.LoginUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

  @ExperimentalCoroutinesApi
  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  var loginUseCase: LoginUseCase = mock()

  private lateinit var loginViewModel: LoginViewModel

  @Before
  fun setUp() {
    loginViewModel = LoginViewModel(loginUseCase)
  }

  @Test
  fun login_invalidEmail() {
    val email = "rick_sanchez"
    val password = "Password123"

    loginViewModel.attemptLogin(email, password)

    assertTrue(loginViewModel.loginFormState.value is LoginFormState.EmailError)
    assertTrue(loginViewModel.loginFormState.value !is LoginFormState.PasswordError)
  }

  @Test
  fun login_invalidPassword() {
    val email = "rick_sanchez@gmail.com"
    val password = "pss"

    loginViewModel.attemptLogin(email, password)

    assertTrue(loginViewModel.loginFormState.value is LoginFormState.PasswordError)
    assertTrue(loginViewModel.loginFormState.value !is LoginFormState.EmailError)
  }

  @Test
  fun login_requestSuccessful() = runTest {
    val email = "rick_sanchez@gmail.com"
    val password = "Password123"

    whenever(loginUseCase.invoke(any(), any()))
      .thenReturn(Result.Success(User(name = "Rick Sanchez", email = "rick_sanchez@gmail.com")))

    loginViewModel.attemptLogin(email, password)

    verify(loginUseCase, times(1)).invoke(any(), any())

    assertThat(loginViewModel.loginState.value is LoginState.Success, `is`(true))
  }

  @Test
  fun login_requestError() = runTest {
    val email = "rick@gmail.com"
    val password = "PasswordIncorrect"

    whenever(loginUseCase.invoke(any(), any()))
      .thenReturn(Result.Error("Unknown error message."))

    loginViewModel.attemptLogin(email, password)

    verify(loginUseCase, times(1)).invoke(any(), any())

    assertThat(loginViewModel.loginState.value is LoginState.Error, `is`(true))
  }
}