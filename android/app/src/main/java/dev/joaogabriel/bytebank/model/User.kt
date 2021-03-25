package dev.joaogabriel.bytebank.model

data class User(
    var accountNumber: Int,
    var email: String,
    var password: String,
    var name: String,
    var balance: Float,
    var transactions: List<Transaction>
)
