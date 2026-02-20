package com.erguncoban.cryptoexchangeapp.data.repository

import com.erguncoban.cryptoexchangeapp.data.datasource.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val dataSource: UserRemoteDataSource) {

    suspend fun createUserProfile(uid: String, email: String) : Boolean {
        return dataSource.createUserProfile(uid, email)
    }

    fun getUserBalance(uid: String) : Flow<Double> {
        return dataSource.getUserBalance(uid)
    }

    suspend fun updateBalance(uid: String, amount: Double) : Boolean{
        return dataSource.updateBalance(uid, amount)
    }

}