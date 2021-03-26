package dev.joaogabriel.bytebank.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.FragmentAddBalanceBinding
import dev.joaogabriel.bytebank.model.User
import dev.joaogabriel.bytebank.utils.Resource
import dev.joaogabriel.bytebank.viewmodel.UserViewModel

class AddBalance : DialogFragment() {
    private lateinit var binding: FragmentAddBalanceBinding
    private lateinit var currentUser: User
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddBalanceBinding.bind(view)
        binding.balanceBtnConfirm.setOnClickListener { addBalance() }
        binding.balanceBtnGoBack.setOnClickListener { dismiss() }

        userResponse()
    }

    private fun userResponse() {
        userViewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> if (response.data != null) currentUser = response.data

                is Resource.Error -> dismiss()

                is Resource.Loading -> println("loading add balance")
            }
        })
    }

    private fun addBalance() {
        val balance = binding.balanceTxtBalance.text.toString().toFloat()
        currentUser.balance += balance

        userViewModel.updateBalance(currentUser)
        dismiss()
    }
}