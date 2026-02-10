package com.erguncoban.cryptoexchangeapp.data.repository

import com.erguncoban.cryptoexchangeapp.data.datasource.CoinDataSource
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepository @Inject constructor(private val dataSource: CoinDataSource) {

    suspend fun getCoins() : List<CryptoCoin> {
        return dataSource.getCoins()
    }

    suspend fun getCoinById(id: String) : CryptoCoin? {
        return dataSource.getCoinById(id)
    }

    suspend fun getCoinMarketChart(coinId: String) : Flow<List<Pair<Long, Float>>> = flow {
        try {
            val response = dataSource.getCoinMarketChart(coinId)

            val formattedData = response.prices.map { entry ->
                val timestamp = entry[0].toLong()
                val price = entry[1].toFloat()
                Pair(timestamp, price)
            }

            emit(formattedData)

        }catch (e: Exception){
            e.printStackTrace()
            emit(emptyList())
        }
    }

    suspend fun getCoinDetails(id: String) : CryptoDetailResponse {
        return dataSource.getCoinDetails(id)
    }

}