package dev.joaogabriel.bytebank.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.joaogabriel.bytebank.model.Transaction
import dev.joaogabriel.bytebank.model.User
import dev.joaogabriel.bytebank.model.repository.UserRepository
import dev.joaogabriel.bytebank.model.request.TransactionRequest
import dev.joaogabriel.bytebank.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val transactionRepository = TransactionRequest().makeRequest()
    val userResponse: MutableLiveData<Resource<User>> = MutableLiveData()

    fun createUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userResponse.postValue(Resource.Loading())

            try {
                user.apply {
                    userRepository.createUser(email, password)
                    userRepository.createUserOnFireStore(this)
                }

                userResponse.postValue(Resource.Success(null))
            } catch (exception: Exception) {
                userResponse.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun signIn(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userResponse.postValue(Resource.Loading())

            try {
                userRepository.signIn(email, password)

                userResponse.postValue(Resource.Success(null))
            } catch (exception: Exception) {
                userResponse.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun sendPasswordReset(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userResponse.postValue(Resource.Loading())

            try {
                userRepository.sendPasswordReset(email)

                userResponse.postValue(Resource.Success(null))
            } catch (exception: Exception) {
                userResponse.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun getUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            userResponse.postValue(Resource.Loading())

            try {
                val currentUser = userRepository.getCurrentUser()

                if (currentUser != null && currentUser.email != null) {
                    val user = userRepository.getUserData(currentUser.email!!).toObject(User::class.java)

                    userResponse.postValue(Resource.Success(user))
                }
            } catch (exception: Exception) {
                userResponse.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun updateBalance(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userResponse.postValue(Resource.Loading())

            try {
                userRepository.updateBalance(user)

                userResponse.postValue(Resource.Success(user))
            } catch (exception: Exception) {
                userResponse.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun getUserTransactions(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val transactions = transactionRepository.getTransactions()

                if (user.transactions?.size != transactions.size) {
                    user.transactions = transactions

                    userResponse.postValue(Resource.Success(user))
                }
            } catch (exception: Exception) {
                userResponse.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun addTransaction(user: User, transaction: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = transactionRepository.addTransaction(2000, transaction)
                user.transactions?.add(response)
                user.balance -= transaction.value

                updateBalance(user)
            } catch (exception: Exception) {
                userResponse.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

    fun signOut() {
        userRepository.signOut()
    }
}