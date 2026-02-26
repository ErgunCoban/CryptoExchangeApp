package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.AssetItemUiModel
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioItem
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.erguncoban.cryptoexchangeapp.data.repository.PortfolioRepository
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val authRepository: FirebaseAuthRepository,
    private val userRepository: UserRepository,
    private val coinRepository: CoinRepository,
    private val portfolioRepository: PortfolioRepository
) : ViewModel() {

    private val _balance = MutableStateFlow(0.0)
    val balance = _balance.asStateFlow()

    private val _portfolioItems = MutableStateFlow<List<PortfolioItem>>(emptyList())
    val portfolioItems = _portfolioItems.asStateFlow()

    private val _totalPortfolioValue = MutableStateFlow(0.0)
    val totalPortfolioValue = _totalPortfolioValue.asStateFlow()

    private val _btcEquivalent = MutableStateFlow(0.0)
    val btcEquivalent = _btcEquivalent.asStateFlow()

    private val _assetUiList = MutableStateFlow<List<AssetItemUiModel>>(emptyList())
    val assetUiList = _assetUiList.asStateFlow()

    init {
        startListeningBalance()
        startListeningPortfolio()
        observeTotalValue()
    }

    private fun startListeningBalance() {
        val uid = authRepository.getCurrentUserUid()
        uid?.let { id ->
            viewModelScope.launch {
                userRepository.getUserBalance(id).collect { newBalance ->
                    _balance.value = newBalance
                }
            }
        }
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
            combine(balance, portfolioItems) { currentUsd, items ->
                var totalCoinValue = 0.0
                val uiList = mutableListOf<AssetItemUiModel>()

                items.forEach { item ->
                    try {
                        val coinData = coinRepository.getCoinById(item.coinId)
                        val currentPrice = coinData?.current_price ?: 0.0
                        val itemTotalValue = item.amount * currentPrice

                        totalCoinValue += itemTotalValue

                        uiList.add(
                            AssetItemUiModel(
                                coinId = item.coinId,
                                name = coinData?.name ?: "",
                                symbol = coinData?.symbol?.uppercase() ?: item.coinId.uppercase(),
                                amount = item.amount,
                                currentPrice = currentPrice,
                                totalValue = itemTotalValue,
                                imageUrl = coinData?.imageUrl
                            )
                        )

                    } catch (e: Exception) {
                        Log.e("ASSETS_ERROR", "${item.coinId} fiyatı alınamadı: ${e.message}")
                    }
                }
                _assetUiList.value = uiList
                currentUsd + totalCoinValue

            }.collect { finalTotalValue ->
                _totalPortfolioValue.value = finalTotalValue
                calculateBtcEquivalent(finalTotalValue)
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