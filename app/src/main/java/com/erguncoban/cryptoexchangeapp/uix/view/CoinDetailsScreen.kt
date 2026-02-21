package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.CoinDetailsTopBar
import com.erguncoban.cryptoexchangeapp.components.CoinInfoSection
import com.erguncoban.cryptoexchangeapp.components.CoinPriceSection
import com.erguncoban.cryptoexchangeapp.components.CoinTransactionBar
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.CoinDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(navController: NavController,
                      coinId: String,
                      viewModel: CoinDetailsViewModel = hiltViewModel(key = coinId)){

    val coinDetail by viewModel.coinDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val chartData by viewModel.chartData.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadCoinDetails(coinId)
        viewModel.loadChart(coinId)
    }

    Scaffold(
        topBar = {
            CoinDetailsTopBar(
                navController,
                viewModel,
                coinDetail,
                selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                }
            )
        },
        bottomBar = {
            coinDetail?.let { detail ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    CoinTransactionBar(detail, navController)
                }
            }
        }
    ) {
        paddingValues ->

        if (isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center){
                    CircularProgressIndicator(color = YellowTheme)
            }
        }else{
            coinDetail?.let { detail ->

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    when(selectedTabIndex) {
                        0 -> {
                            CoinPriceSection(
                                detail = detail,
                                chartData = chartData,
                                viewModel = viewModel
                            )
                        }

                        1 -> {
                            CoinInfoSection(detail = detail)
                        }
                    }

                }
            }
        }
    }

}

