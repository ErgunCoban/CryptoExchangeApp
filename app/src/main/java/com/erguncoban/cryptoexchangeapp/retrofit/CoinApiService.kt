package com.erguncoban.cryptoexchangeapp.retrofit

import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoinApiService {

    @GET("coins/markets")
    suspend fun getTopCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") per_page: Int = 250,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ) : List<CryptoCoin>

    @GET("coins/markets")
    suspend fun getCoinById(
        @Header("x-cg-demo-api-key") apiKey: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("ids") id: String,
    ) : List<CryptoCoin>

}