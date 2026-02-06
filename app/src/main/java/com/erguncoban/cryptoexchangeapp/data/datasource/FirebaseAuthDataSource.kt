package com.erguncoban.cryptoexchangeapp.data.datasource

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun login(email: String, password: String) : Boolean{
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            true
        }catch (e: Exception){
            false
        }
    }

    suspend fun signup(email: String, password: String) : Boolean{
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            true
        }catch (e: Exception){
            false
        }
    }

    fun isUserLoggedIn() : Boolean{
        return firebaseAuth.currentUser != null
    }

    fun logout(){
        firebaseAuth.signOut()
    }

}