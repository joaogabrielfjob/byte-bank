package dev.joaogabriel.bytebank.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.utils.Resource
import dev.joaogabriel.bytebank.viewmodel.UserViewModel

class Splash : Fragment(R.layout.fragment_splash) {
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getUserData()
        userResponse()
    }

    private fun userResponse() {
        userViewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> openHome()

                is Resource.Error -> openSignIn()

                is Resource.Loading -> println("loading splash")
            }
        })
    }

    private fun openHome() {
        Navigation.findNavController(requireView()).navigate(R.id.splashToHome)
    }

    private fun openSignIn() {
        Navigation.findNavController(requireView()).navigate(R.id.splashToSignIn)
    }
}