package dev.joaogabriel.bytebank.model.repository

import dev.joaogabriel.bytebank.model.Transaction
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TransactionRepository {

    @GET("transactions")
    suspend fun getTransactions(): ArrayList<Transaction>

    @POST("transactions")
    suspend fun addTransaction(
        @Header("password") password: Int,
        @Body transaction: Transaction
    ): Transaction
}