package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoDetailResponse
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(private val repository: CoinRepository,
                                               private val authRepository: FirebaseAuthRepository,
                                               private val userRepository: UserRepository) : ViewModel() {

    private val _coinDetail = MutableStateFlow<CryptoDetailResponse?>(null)
    val coinDetail = _coinDetail.asStateFlow()

    private val _favoriteCoins = MutableStateFlow<List<String>>(emptyList())
    val favoriteCoins = _favoriteCoins.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _chartData = MutableStateFlow<List<Pair<Long, Float>>>(emptyList())
    val chartData: StateFlow<List<Pair<Long, Float>>> = _chartData

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        listenFavoriteCoins()
    }

    fun loadCoinDetails(id: String){
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val detailResponse = repository.getCoinDetails(id)
                _coinDetail.value = detailResponse
            } catch (e: Exception){
                _errorMessage.value = "Data couldn't be received: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
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

    private fun listenFavoriteCoins(){
        val uid = authRepository.getCurrentUserUid()
        if (uid != null){
            viewModelScope.launch {
                userRepository.getFavoriteCoins(uid).collect { favoriteList ->
                    _favoriteCoins.value = favoriteList
                }
            }
        }
    }

    fun onFavoriteClick(coinID: String){
        val uid = authRepository.getCurrentUserUid()
        if (uid != null){
            val isCurrentlyFavorite = _favoriteCoins.value.contains(coinID)

            val isAdd = !isCurrentlyFavorite

            viewModelScope.launch {
                userRepository.toggleFavorite(uid, coinID, isAdd)
            }
        }
    }

}