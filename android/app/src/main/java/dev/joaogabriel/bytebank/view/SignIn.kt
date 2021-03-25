package dev.joaogabriel.bytebank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.FragmentSignInBinding

class SignIn : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignInBinding.bind(view)

        binding.signInBtnSignUp.setOnClickListener { openSignUp() }
    }

    private fun openSignUp() {
        Navigation.findNavController(requireView()).navigate(R.id.signInToSignUp)
    }
}