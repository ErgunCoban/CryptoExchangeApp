package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioItem
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.erguncoban.cryptoexchangeapp.data.repository.PortfolioRepository
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeScreenViewModel @Inject constructor(private val repository: CoinRepository,
                                               private val authRepository: FirebaseAuthRepository,
                                               private val userRepository: UserRepository,
                                               private val portfolioRepository: PortfolioRepository): ViewModel() {

    private val _coinList = MutableStateFlow<List<CryptoCoin>>(emptyList())
    val coinList = _coinList.asStateFlow()

    private val _balance = MutableStateFlow(0.0)
    val balance = _balance.asStateFlow()

    private val _portfolioItems = MutableStateFlow<List<PortfolioItem>>(emptyList())
    val portfolioItems = _portfolioItems.asStateFlow()

    init {
        loadCoin()
        startListeningBalance()
        startListeningPortfolio()
    }

    fun loadCoin(){
        viewModelScope.launch {
            try {
                val result = repository.getCoins()
                _coinList.value = result
                Log.e("API_SUCCESS", "Number Of Coins Received: ${result.size}")
            }catch (e: Exception){
                Log.e("API_FAILED", "Failed The Capture Data: $e")
            }
        }
    }

    private fun startListeningBalance(){

        val uid = authRepository.getCurrentUserUid()

        if (uid != null){
            viewModelScope.launch {
                try {
                    userRepository.getUserBalance(uid).collect { newBalance ->
                        _balance.value = newBalance
                        Log.e("FIRESTORE_SUCCESS", "Current Balance: $newBalance")
                    }
                }catch (e: Exception){
                    Log.e("FIRESTORE_FAILED", "Balance couldn't be withdrawn: $e")
                }
            }
        }
    }

    private fun startListeningPortfolio() {
        viewModelScope.launch {
            try {
                portfolioRepository.getPortfolioFlow().collect { items ->
                    _portfolioItems.value = items
                    Log.e("FIRESTORE_SUCCESS", "Portfolio updated, item count: ${items.size}")
                }
            } catch (e: Exception) {
                Log.e("FIRESTORE_FAILED", "Portfolio couldn't be withdrawn: $e")
            }
        }
    }

    fun buyCoin(coinId: String, amount: Double, currentPrice: Double){
        viewModelScope.launch {
            val result = portfolioRepository.buyCoin(coinId, amount, currentPrice)

            if (result.isFailure){
                println("Buying Error: ${result.exceptionOrNull()?.message}")
            }else{
                println("Buying Success")
            }
        }

    }

    fun sellCoin(coinId: String, amount: Double, currentPrice: Double){
        viewModelScope.launch {
            val result = portfolioRepository.sellCoin(coinId, amount, currentPrice)

            if (result.isFailure){
                println("Selling Error: ${result.exceptionOrNull()?.message}")
            }else{
                println("Selling success")
            }
        }
    }

}