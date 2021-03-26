package dev.joaogabriel.bytebank.model

data class Transaction(
    var value: Float,
    var contact: Contact,
    var dateTime: String
)
