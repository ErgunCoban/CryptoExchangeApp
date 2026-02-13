package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeScreenViewModel @Inject constructor(private val repository: CoinRepository): ViewModel() {

    private val _coinList = MutableStateFlow<List<CryptoCoin>>(emptyList())
    val coinList = _coinList.asStateFlow()

    init {
        loadCoin()
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

}