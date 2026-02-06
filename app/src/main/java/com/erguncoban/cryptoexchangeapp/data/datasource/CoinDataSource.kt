package com.erguncoban.cryptoexchangeapp.data.datasource

import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.retrofit.CoinApiService
import javax.inject.Inject

class CoinDataSource @Inject constructor(private val api: CoinApiService) {

    suspend fun getCoins() : List<CryptoCoin> {
        return api.getTopCoins()
    }

}