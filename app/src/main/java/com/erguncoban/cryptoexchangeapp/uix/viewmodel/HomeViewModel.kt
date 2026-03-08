package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.entity.PortfolioSummary
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.erguncoban.cryptoexchangeapp.data.repository.PortfolioRepository
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import com.erguncoban.cryptoexchangeapp.usecase.GetPortfolioSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CoinRepository,
                                        private val authRepository: FirebaseAuthRepository,
                                        private val userRepository: UserRepository,
                                        private val portfolioRepository: PortfolioRepository,
                                        private val getPortfolioSummaryUseCase: GetPortfolioSummaryUseCase, ) : ViewModel() {

    private val _coinList = MutableStateFlow<List<CryptoCoin>>(emptyList())
    val coinList = _coinList.asStateFlow()

    private val _balance = MutableStateFlow(0.0)
    val balance = _balance.asStateFlow()

    private val _totalPortfolioValue = MutableStateFlow(0.0)
    val totalPortfolioValue = _totalPortfolioValue.asStateFlow()

    val portfolioSummary: StateFlow<PortfolioSummary> = getPortfolioSummaryUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PortfolioSummary()
        )

    init {
        loadCoins()
        startListeningBalanceAndPortfolio()
    }

    private fun loadCoins(){
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

    private fun startListeningBalanceAndPortfolio(){

        val uid = authRepository.getCurrentUserUid()

        if (uid != null) {
            viewModelScope.launch {

                //combine sayesinde 3 farklı flowu birleştirdik.
                //Herhangi birisinin değerinin değişmesi halinde bu blok çalışır ve güncelleme sağlanır.
                combine(
                    userRepository.getUserBalance(uid),
                    portfolioRepository.getPortfolioFlow(),
                    coinList
                ) { currentBalance, portfolioItems, currentCoins ->

                    _balance.value = currentBalance

                    var totalCryptoValue = 0.0

                    for (item in portfolioItems) {
                        val coinPrice = currentCoins.find { it.id == item.coinId }?.current_price ?: 0.0
                        totalCryptoValue += (item.amount * coinPrice)
                    }

                    currentBalance + totalCryptoValue

                }.collect { calculatedTotalValue ->
                    _totalPortfolioValue.value = calculatedTotalValue
                    Log.e("PORTFOLIO_SUCCESS", "Total Portfolio Value: $calculatedTotalValue")
                }
            }
        } else {
            Log.e("AUTH_ERROR", "User ID not found")
        }

    }


}