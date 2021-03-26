package dev.joaogabriel.bytebank.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.FragmentAddTransactionBinding
import dev.joaogabriel.bytebank.model.Contact
import dev.joaogabriel.bytebank.model.Transaction
import dev.joaogabriel.bytebank.model.User
import dev.joaogabriel.bytebank.utils.Resource
import dev.joaogabriel.bytebank.viewmodel.UserViewModel
import java.util.Date

class AddTransaction : DialogFragment() {
    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var currentUser: User
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddTransactionBinding.bind(view)
        binding.transactionBtnConfirm.setOnClickListener { addTransaction() }
        binding.transactionBtnGoBack.setOnClickListener { dismiss() }

        userResponse()
    }

    private fun userResponse() {
        userViewModel.userResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> if (response.data != null) currentUser = response.data

                is Resource.Error -> println(response.message)

                is Resource.Loading -> println("loading add transaction")
            }
        })
    }

    private fun addTransaction() {
        val value = binding.transactionTxtValue.text.toString().toFloat()

        if (currentUser.balance < value) {
            Toast.makeText(requireContext(), "Saldo insuficiente", Toast.LENGTH_LONG).show()
        } else {
            val transaction = Transaction(
                value,
                Contact(currentUser.name, currentUser.accountNumber),
                Date().toString()
            )

            userViewModel.addTransaction(currentUser, transaction)
        }

        dismiss()
    }
}