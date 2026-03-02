package com.erguncoban.cryptoexchangeapp.data.repository

import com.erguncoban.cryptoexchangeapp.data.datasource.PortfolioRemoteDataSource
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PortfolioRepository @Inject constructor(private val dataSource: PortfolioRemoteDataSource){

    suspend fun buyCoin (coinId: String, amount: Double, currentPrice: Double) : Result<Unit>
        = dataSource.buyCoin(coinId, amount, currentPrice)

    suspend fun sellCoin(coinId: String, amount: Double, currentPrice: Double) : Result<Unit>
        = dataSource.sellCoin(coinId, amount, currentPrice)

    suspend fun withdraw(amount: Double, currentBalance: Double) : Result<Unit>
        = dataSource.withdraw(amount, currentBalance)

    fun getPortfolioFlow(): Flow<List<PortfolioItem>>
        = dataSource.getPortfolioFlow()

}