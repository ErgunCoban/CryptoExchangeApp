package com.erguncoban.cryptoexchangeapp.data.datasource

import com.erguncoban.cryptoexchangeapp.BuildConfig
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoDetailResponse
import com.erguncoban.cryptoexchangeapp.data.entity.MarketChartResponse
import com.erguncoban.cryptoexchangeapp.retrofit.CoinApiService
import javax.inject.Inject

class CoinDataSource @Inject constructor(private val api: CoinApiService) {

    private val API_KEY = BuildConfig.API_KEY

    suspend fun getCoins() : List<CryptoCoin> {
        return api.getTopCoins()
    }

    suspend fun getCoinById(id: String) : CryptoCoin? {
        return api.getCoinById(API_KEY, id = id).firstOrNull()
    }

    suspend fun getCoinMarketChart(id: String) : MarketChartResponse {
        return api.getCoinMarketChart(API_KEY, id)
    }

    suspend fun getCoinDetails(id: String) : CryptoDetailResponse {
        return api.getCoinDetails(API_KEY, id)
    }

}