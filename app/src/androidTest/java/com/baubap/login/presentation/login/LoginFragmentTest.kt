package com.baubap.login.presentation.login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.baubap.login.R
import com.baubap.login.data.repository.FakeLoginRepositoryImpl
import com.baubap.login.di.AppModuleBinds
import com.baubap.login.domain.repository.LoginRepository
import com.baubap.login.presentation.main.MainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(AppModuleBinds::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class LoginFragmentTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @BindValue
  @JvmField
  var loginRepository: LoginRepository = FakeLoginRepositoryImpl()

  @Before
  fun setUp() {
    hiltRule.inject()

    launchActivity()
  }

  @Test
  fun login_invalidEmail() {
    onView(withId(R.id.emailView)).perform(typeText("rick"), closeSoftKeyboard())
    onView(withId(R.id.passwordView)).perform(typeText("Password123"), closeSoftKeyboard())
    onView(withId(R.id.loginButton)).perform(click())

    onView(withId(R.id.emailView)).check(matches(hasErrorText("Not a valid email")))
  }

  @Test
  fun login_invalidPassword() {
    onView(withId(R.id.emailView)).perform(typeText("rick_sanchez@gmail.com"), closeSoftKeyboard())
    onView(withId(R.id.passwordView)).perform(typeText("nope"), closeSoftKeyboard())
    onView(withId(R.id.loginButton)).perform(click())

    onView(withId(R.id.passwordView)).check(matches(hasErrorText("Password must be >5 characters")))
  }

  @Test
  fun login_requestSuccessful() {
    (loginRepository as FakeLoginRepositoryImpl).setReturnError(false)

    onView(withId(R.id.emailView)).perform(typeText("rick_sanchez@gmail.com"), closeSoftKeyboard())
    onView(withId(R.id.passwordView)).perform(typeText("Password123"), closeSoftKeyboard())
    onView(withId(R.id.loginButton)).perform(click())

    onView(withText("User Rick Sanchez successfully logged in"))
      .check(matches(isDisplayed()))
  }

  @Test
  fun login_requestError() {
    (loginRepository as FakeLoginRepositoryImpl).setReturnError(true)

    onView(withId(R.id.emailView)).perform(typeText("invalid_mail@gmail.com"), closeSoftKeyboard())
    onView(withId(R.id.passwordView)).perform(typeText("invalidPass"), closeSoftKeyboard())
    onView(withId(R.id.loginButton)).perform(click())

    onView(withText(R.string.error_login_failed))
      .check(matches(isDisplayed()))
  }

  private fun launchActivity(): ActivityScenario<MainActivity>? {
    return ActivityScenario.launch(MainActivity::class.java)
  }
}