package com.erguncoban.cryptoexchangeapp.uix.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.data.repository.CoinRepository
import com.erguncoban.cryptoexchangeapp.data.repository.FirebaseAuthRepository
import com.erguncoban.cryptoexchangeapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val coinRepository: CoinRepository,
                                             private val userRepository: UserRepository,
                                             private val authRepository: FirebaseAuthRepository) : ViewModel() {

    private val _favoriteCoins = MutableStateFlow<List<String>>(emptyList())
    val favoriteCoins = _favoriteCoins.asStateFlow()

    private val _favoriteCoinDetails = MutableStateFlow<List<CryptoCoin>>(emptyList())
    val favoriteCoinDetails = _favoriteCoinDetails.asStateFlow()

    private var allCoinsCache: List<CryptoCoin> = emptyList()

    init {
        loadCoinsAndListenFavorite()
    }

    private fun loadCoinsAndListenFavorite(){
        val uid = authRepository.getCurrentUserUid()
        if (uid != null){
            viewModelScope.launch {
                try {
                    allCoinsCache = coinRepository.getCoins()
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Coinler API'den çekilemedi: $e")
                }

                userRepository.getFavoriteCoins(uid).collect { favoriteList ->
                    _favoriteCoins.value = favoriteList

                    val filteredCoins = allCoinsCache.filter { coin ->
                        _favoriteCoins.value.contains(coin.id)
                    }
                    _favoriteCoinDetails.value = filteredCoins
                }
            }
        }
    }

}