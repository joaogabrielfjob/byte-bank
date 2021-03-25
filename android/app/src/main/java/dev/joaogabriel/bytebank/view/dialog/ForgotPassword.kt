package dev.joaogabriel.bytebank.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.FragmentForgotPasswordBinding
import dev.joaogabriel.bytebank.utils.Resource
import dev.joaogabriel.bytebank.viewmodel.UserViewModel

class ForgotPassword : DialogFragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentForgotPasswordBinding.bind(view)

        binding.passBtnConfirm.setOnClickListener { sendPasswordReset() }
        binding.passBtnGoBack.setOnClickListener { dismiss() }

        userResponse()
    }

    private fun userResponse() {
        userViewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> println(response.data)

                is Resource.Error -> println(response.message)

                is Resource.Loading -> println("loading forgot password")
            }
        })
    }

    private fun sendPasswordReset() {
        val email = binding.passTxtEmail.text.toString()

        userViewModel.sendPasswordReset(email)
    }
}