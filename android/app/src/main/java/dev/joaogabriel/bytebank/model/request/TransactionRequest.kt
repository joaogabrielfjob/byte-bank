package dev.joaogabriel.bytebank.model.request

import dev.joaogabriel.bytebank.model.repository.TransactionRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TransactionRequest {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
    }

    fun makeRequest(): TransactionRepository {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(TransactionRepository::class.java)
    }
}