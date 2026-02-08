package com.erguncoban.cryptoexchangeapp.data.datasource

import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.retrofit.CoinApiService
import javax.inject.Inject

class CoinDataSource @Inject constructor(private val api: CoinApiService) {

    private val API_KEY = "CG-S1BPT9jt9GeyE7nRDMpqDh9z"

    suspend fun getCoins() : List<CryptoCoin> {
        return api.getTopCoins()
    }

    suspend fun getCoinById(id: String) : CryptoCoin? {
        return api.getCoinById(API_KEY, id = id).firstOrNull()
    }

}