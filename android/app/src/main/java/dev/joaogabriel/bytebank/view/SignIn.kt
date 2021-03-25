package dev.joaogabriel.bytebank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.FragmentSignInBinding
import dev.joaogabriel.bytebank.utils.Resource
import dev.joaogabriel.bytebank.viewmodel.UserViewModel

class SignIn : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignInBinding.bind(view)

        binding.signInBtnSignIn.setOnClickListener { signIn() }
        binding.signInBtnSignUp.setOnClickListener { openSignUp() }

        userResponse()
    }

    private fun userResponse() {
        userViewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> println(response.data)

                is Resource.Error -> println(response.message)

                is Resource.Loading -> println("loading sign in")
            }
        })
    }

    private fun signIn() {
        val email = binding.signInTxtEmail.text.toString()
        val password = binding.signInTxtPassword.text.toString()

        userViewModel.signIn(email, password)
    }

    private fun openSignUp() {
        Navigation.findNavController(requireView()).navigate(R.id.signInToSignUp)
    }
}