package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.AssetItemUiModel
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioItem
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import com.erguncoban.cryptoexchangeapp.data.repository.PortfolioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val portfolioRepository: PortfolioRepository
) : ViewModel() {

    private val _portfolioItems = MutableStateFlow<List<PortfolioItem>>(emptyList())
    val portfolioItems = _portfolioItems.asStateFlow()

    private val _totalPortfolioValue = MutableStateFlow(0.0)
    val totalPortfolioValue = _totalPortfolioValue.asStateFlow()

    private val _btcEquivalent = MutableStateFlow(0.0)
    val btcEquivalent = _btcEquivalent.asStateFlow()

    private val _assetUiList = MutableStateFlow<List<AssetItemUiModel>>(emptyList())
    val assetUiList = _assetUiList.asStateFlow()

    init {
        startListeningPortfolio()
        observeTotalValue()
    }

    private fun startListeningPortfolio() {
        viewModelScope.launch {
            portfolioRepository.getPortfolioFlow().collect { items ->
                _portfolioItems.value = items
            }
        }
    }

    private fun observeTotalValue() {
        viewModelScope.launch {
            portfolioItems.collect { items ->
                var totalCoinValue = 0.0
                val uiList = mutableListOf<AssetItemUiModel>()

                items.forEach { item ->
                    try {
                        val currentPrice: Double
                        val name: String
                        val symbol: String
                        val imageUrl: String?

                        if (item.coinId.lowercase() == "usdt"){
                            currentPrice = 1.0
                            name = "Tether"
                            symbol = "USDT"
                            imageUrl = "https://assets.coingecko.com/coins/images/325/large/Usdt.png"
                        }else{
                            val coinData = coinRepository.getCoinById(item.coinId)
                            currentPrice = coinData?.current_price ?: 0.0
                            name = coinData?.name ?: item.coinId.replaceFirstChar { it.uppercase() }
                            symbol = coinData?.symbol?.uppercase() ?: item.coinId.uppercase()
                            imageUrl = coinData ?.imageUrl
                        }

                        val itemTotalValue = item.amount * currentPrice
                        totalCoinValue += itemTotalValue

                        uiList.add(
                            AssetItemUiModel(
                                coinId = item.coinId,
                                name = name,
                                symbol = symbol,
                                amount = item.amount,
                                currentPrice = currentPrice,
                                totalValue = itemTotalValue,
                                imageUrl = imageUrl
                            )
                        )
                    }catch (e: Exception){
                        Log.e("ASSETS_ERROR", "${item.coinId} alınamadı: ${e.message}")
                    }
                }

                _assetUiList.value = uiList
                _totalPortfolioValue.value = totalCoinValue
                calculateBtcEquivalent(totalCoinValue)
            }
        }
    }

    fun calculateBtcEquivalent(totalValue: Double) {
        if (totalValue <= 0.0) {
            _btcEquivalent.value = 0.0
            return
        }
        viewModelScope.launch {
            try {
                val btcData = coinRepository.getCoinById("bitcoin")
                val btcPrice = btcData?.current_price ?: 0.0
                if (btcPrice > 0.0) {
                    _btcEquivalent.value = totalValue / btcPrice
                }
            } catch (e: Exception) {
                Log.e("ASSETS_ERROR", "BTC fiyatı alınamadı", e)
            }
        }
    }
}