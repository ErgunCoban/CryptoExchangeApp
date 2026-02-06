package com.erguncoban.cryptoexchangeapp.data.repository

import com.erguncoban.cryptoexchangeapp.data.datasource.CoinDataSource
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import javax.inject.Inject

class CoinRepository @Inject constructor(private val dataSource: CoinDataSource) {

    suspend fun getCoins() : List<CryptoCoin> {
        return dataSource.getCoins()
    }

}