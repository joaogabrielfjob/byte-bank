package dev.joaogabriel.bytebank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.FragmentHomeBinding
import dev.joaogabriel.bytebank.model.User
import dev.joaogabriel.bytebank.utils.Resource
import dev.joaogabriel.bytebank.viewmodel.UserViewModel

class Home : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        userViewModel.getUserData()
        userResponse()
    }

    private fun userResponse() {
        userViewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> setUserData(response.data)

                is Resource.Error -> println(response.message)

                is Resource.Loading -> println("loading sign in")
            }
        })
    }

    private fun setUserData(user: User?) {
        if (user != null) {
            val balance = String.format(resources.getString(R.string.txtBalance), user.balance)

            binding.homeTxtName.text = user.name
            binding.homeTxtBalance.text = balance
        }
    }
}