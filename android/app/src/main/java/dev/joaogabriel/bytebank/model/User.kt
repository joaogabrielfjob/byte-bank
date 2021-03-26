package dev.joaogabriel.bytebank.model

data class User(
    var accountNumber: Int = 0,
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var balance: Float = 0f,
    var transactions: ArrayList<Transaction>? = null
)
