package com.baubap.login.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.baubap.login.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

  private val viewModel by viewModels<LoginViewModel>()

  private lateinit var binding: LoginFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = LoginFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }
}