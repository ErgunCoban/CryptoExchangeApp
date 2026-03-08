package com.erguncoban.cryptoexchangeapp.data.repository

import com.erguncoban.cryptoexchangeapp.data.datasource.UserRemoteDataSource
import com.erguncoban.cryptoexchangeapp.data.entity.ProfileUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val dataSource: UserRemoteDataSource) {

    suspend fun createUserProfile(uid: String, email: String) : Boolean {
        return dataSource.createUserProfile(uid, email)
    }

    fun getUserBalance(uid: String) : Flow<Double> {
        return dataSource.getUserBalance(uid)
    }

    suspend fun updateBalance(amount: Double) : Boolean{
        return dataSource.updateBalance(amount)
    }

    suspend fun toggleFavorite(uid: String, coinID: String, isAdd: Boolean) : Boolean{
        return dataSource.toggleFavorite(uid, coinID, isAdd)
    }

    fun getFavoriteCoins(uid: String) : Flow<List<String>> {
        return dataSource.getFavoriteCoins(uid)
    }

    suspend fun getUserProfile(uid: String): ProfileUiState?{
        return dataSource.getUserProfile(uid)
    }

}