package dev.joaogabriel.bytebank.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.joaogabriel.bytebank.model.User
import dev.joaogabriel.bytebank.model.repository.UserRepository
import dev.joaogabriel.bytebank.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()
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
}