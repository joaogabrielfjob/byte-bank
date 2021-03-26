package dev.joaogabriel.bytebank.model.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dev.joaogabriel.bytebank.model.User
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun createUser(email: String, password: String): AuthResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

    suspend fun signIn(email: String, password: String): AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

    suspend fun getUserData(email: String): DocumentSnapshot = firebaseFirestore.collection("users").document(email).get().await()

    fun createUserOnFireStore(user: User) = firebaseFirestore.collection("users").document(user.email).set(user)

    fun sendPasswordReset(email: String) = firebaseAuth.sendPasswordResetEmail(email)

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun addBalance(user: User) = firebaseFirestore.collection("users").document(user.email).update("balance", user.balance)

    fun signOut() = firebaseAuth.signOut()
}