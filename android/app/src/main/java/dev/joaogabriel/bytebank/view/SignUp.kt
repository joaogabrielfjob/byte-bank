package dev.joaogabriel.bytebank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.FragmentSignUpBinding
import dev.joaogabriel.bytebank.model.User
import dev.joaogabriel.bytebank.utils.Resource
import dev.joaogabriel.bytebank.viewmodel.UserViewModel
import kotlin.math.floor

class SignUp : Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignUpBinding.bind(view)
        binding.signUpBtnConfirm.setOnClickListener { createUser() }
        binding.signUpBtnGoBack.setOnClickListener { openSignIn() }

        userResponse()
    }

    private fun userResponse() {
        userViewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> openHome()

                is Resource.Error -> println(response.message)

                is Resource.Loading -> println("loading sign up")
            }
        })
    }

    private fun createUser() {
        userViewModel.createUser(getUserData())
    }

    private fun getUserData(): User {
        val accountNumber = floor(Math.random() * 1000).toInt()
        val email = binding.signUpTxtEmail.text.toString()
        val password = binding.signUpTxtPassword.text.toString()
        val name = binding.signUpTxtName.text.toString()
        val balance = binding.signUpTxtBalance.text.toString().toFloat()

        return User(accountNumber, email, password, name, balance)
    }

    private fun openSignIn() {
        Navigation.findNavController(requireView()).navigate(R.id.signUpToSignIn)
    }

    private fun openHome() {
        Navigation.findNavController(requireView()).navigate(R.id.signUpToHome)
    }
}