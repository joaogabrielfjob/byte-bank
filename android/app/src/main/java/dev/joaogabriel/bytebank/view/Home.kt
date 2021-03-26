package dev.joaogabriel.bytebank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.FragmentHomeBinding
import dev.joaogabriel.bytebank.model.User
import dev.joaogabriel.bytebank.utils.Resource
import dev.joaogabriel.bytebank.view.dialog.AddBalance
import dev.joaogabriel.bytebank.viewmodel.UserViewModel

class Home : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private var showAdd = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        binding.homeBtnAdd.setOnClickListener {  showAddOptions()  }
        binding.homeBtnAddBalance.setOnClickListener { openDialogBalance() }

        userViewModel.getUserData()
        userResponse()
    }

    private fun userResponse() {
        userViewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> setUserData(response.data)

                is Resource.Error -> println(response.message)

                is Resource.Loading -> println("loading home")
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

    private fun openDialogBalance() {
        AddBalance().show(requireActivity().supportFragmentManager, "Add Balance Dialog")
    }

    private fun showAddOptions() {
        if (showAdd) {
            showAdd = false
            binding.homeBtnAddBalance.visibility = View.VISIBLE
            binding.homeBtnAddTransaction.visibility = View.VISIBLE
        } else {
            showAdd = true
            binding.homeBtnAddBalance.visibility = View.INVISIBLE
            binding.homeBtnAddTransaction.visibility = View.INVISIBLE
        }
    }
}