package dev.joaogabriel.bytebank.model

import com.google.type.DateTime

data class Transaction(
    var value: Float,
    var contact: User,
    var dateTime: DateTime
)
