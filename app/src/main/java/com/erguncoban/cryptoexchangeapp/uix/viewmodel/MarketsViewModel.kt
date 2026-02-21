package com.erguncoban.cryptoexchangeapp.uix.viewmodel

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
class MarketsViewModel @Inject constructor(private val repository: CoinRepository,
                                           private val authRepository: FirebaseAuthRepository,
                                           private val userRepository: UserRepository) : ViewModel() {

    private val _coinList = MutableStateFlow<List<CryptoCoin>>(emptyList())
    val coinList = _coinList.asStateFlow()

    private val _favoriteCoins = MutableStateFlow<List<String>>(emptyList())
    val favoriteCoins = _favoriteCoins.asStateFlow()

    init {
        loadCoins()
    }

    private fun loadCoins(){
        viewModelScope.launch {
            try {
                val result = repository.getCoins()
                _coinList.value = result
            }catch (e: Exception){

            }
        }
    }


}