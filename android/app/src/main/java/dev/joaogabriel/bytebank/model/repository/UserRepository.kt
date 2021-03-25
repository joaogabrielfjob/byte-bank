package dev.joaogabriel.bytebank.model.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import dev.joaogabriel.bytebank.model.User
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun createUser(email: String, password: String): AuthResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

    suspend fun signIn(email: String, password: String): AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

    fun createUserOnFireStore(user: User) = firebaseFirestore.collection("users").document().set(user)

    fun sendPasswordReset(email: String) = firebaseAuth.sendPasswordResetEmail(email)
}