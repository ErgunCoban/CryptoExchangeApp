package com.erguncoban.cryptoexchangeapp.retrofit

import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoDetailResponse
import com.erguncoban.cryptoexchangeapp.data.entity.MarketChartResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
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

    @GET("coins/{id}/market_chart")
    suspend fun getCoinMarketChart(
        @Header("x-cg-demo-api-key") apiKey: String,
        @Path("id") coinId: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: String = "7"
    ) : MarketChartResponse

    @GET("coins/{id}")
    suspend fun getCoinDetails(
        @Header("x-cg-demo-api-key") apiKey: String,
        @Path("id") coinId: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = true,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false
    ) : CryptoDetailResponse

}