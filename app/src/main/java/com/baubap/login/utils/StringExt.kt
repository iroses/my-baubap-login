package com.baubap.login.utils

import android.util.Patterns

fun String.isInvalidEmail(): Boolean {
  return !isEmailValid()
}

private fun String.isEmailValid(): Boolean {
  return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isInvalidPassword(): Boolean {
  return !isPasswordValid()
}

private fun String.isPasswordValid(): Boolean {
  return this.length > 5
}