package dev.joaogabriel.bytebank.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.joaogabriel.bytebank.R
import dev.joaogabriel.bytebank.databinding.TransactionsItemsBinding
import dev.joaogabriel.bytebank.model.Transaction
import kotlin.collections.ArrayList

class TransactionAdapter(private val transactions: ArrayList<Transaction>, private val context: Context) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>()  {

    inner class TransactionViewHolder(val binding: TransactionsItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = TransactionsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        with(holder) {
            with(transactions[position]) {
                val value = String.format(context.getString(R.string.txtValue), value)
                val accountNumber = String.format(context.getString(R.string.txtAccountNumber), contact.accountNumber)
                val dateFormat = dateTime.split(" ")[0]
                val date = String.format(context.getString(R.string.date), dateFormat)

                binding.itemTxtValue.text = value
                binding.itemTxtNumberAccount.text = accountNumber
                binding.itemTxtDate.text = date
            }
        }
    }

    override fun getItemCount() = transactions.size
}