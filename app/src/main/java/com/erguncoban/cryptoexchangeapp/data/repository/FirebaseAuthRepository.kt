package com.erguncoban.cryptoexchangeapp.data.repository

import com.erguncoban.cryptoexchangeapp.data.datasource.FirebaseAuthDataSource
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(private val authDataSource: FirebaseAuthDataSource) {

    suspend fun login(email: String, password: String) : Boolean{
        return authDataSource.login(email, password)
    }

    suspend fun signup(email: String, password: String) : Boolean{
        return authDataSource.signup(email, password)
    }

    fun isUserLoggedIn() : Boolean{
        return authDataSource.isUserLoggedIn()
    }

    fun logout(){
        authDataSource.logout()
    }

    fun getCurrentUserUid() : String?{
        return authDataSource.getCurrentUserUid()
    }

}