package com.erguncoban.cryptoexchangeapp.uix.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.CoinDetailsTopBar
import com.erguncoban.cryptoexchangeapp.components.LineChart
import com.erguncoban.cryptoexchangeapp.components.PriceHeader
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.CoinDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(navController: NavController, coinId: String, viewModel: CoinDetailsViewModel = hiltViewModel(key = coinId)){

    val coin by viewModel.coin.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val chartData by viewModel.chartData.collectAsState()

    Log.e("Gelen coin id: ", coinId)

    LaunchedEffect(Unit) {
        viewModel.loadCoin(coinId)
        viewModel.loadChart(coinId)
    }

    Scaffold(
        topBar = {
            CoinDetailsTopBar(navController, coin)
        }
    ) {
        paddingValues ->

        if (isLoading){
            CircularProgressIndicator()
        }else{
            coin?.let { coin ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { PriceHeader(coin) }

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    item {
                        if (chartData.isNotEmpty()) {
                            LineChart(
                                data = chartData,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Grafik Yükleniyor...", color = CryptoGray)
                            }
                        }
                    }
                }
            }
        }
    }

}