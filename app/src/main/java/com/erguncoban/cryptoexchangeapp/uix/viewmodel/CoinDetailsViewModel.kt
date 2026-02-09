package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel() {

    private val _coin = MutableStateFlow<CryptoCoin?>(null)
    val coin = _coin.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    private val _chartData = MutableStateFlow<List<Pair<Long, Float>>>(emptyList())
    val chartData: StateFlow<List<Pair<Long, Float>>> = _chartData

    fun loadCoin(id: String){
        viewModelScope.launch {
            _isLoading.value = true

            _coin.value = repository.getCoinById(id)

            _isLoading.value = false
        }
    }

    val chartColor: Color
        get() {
            val data = _chartData.value
            if (data.isEmpty()) return Color.Gray
            val firstPrice = data.first().second
            val lastPrice = data.last().second
            return if (lastPrice >= firstPrice) Color.Green else Color.Red
        }

    fun loadChart(coinId: String) {
        viewModelScope.launch {
            repository.getCoinMarketChart(coinId)
                .catch { e ->
                    // Hata olursa boş liste döner, UI bozulmaz
                    e.printStackTrace()
                }
                .collect { data ->
                    _chartData.value = data
                }
        }
    }

}