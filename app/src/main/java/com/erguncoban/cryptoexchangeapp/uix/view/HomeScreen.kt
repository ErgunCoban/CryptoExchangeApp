package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.CoinListItem
import com.erguncoban.cryptoexchangeapp.components.HomeTopBar
import com.erguncoban.cryptoexchangeapp.components.PortfolioCard
import com.erguncoban.cryptoexchangeapp.components.QuickActionSection
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoBlackBackground
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()){

    val coinList by homeViewModel.coinList.collectAsState(initial = emptyList())
    val currentBalance by homeViewModel.balance.collectAsState()

    Scaffold(
        containerColor = CryptoBlackBackground,
        topBar = {
            HomeTopBar(navController)
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        ) {

            item { PortfolioCard(currentBalance) }

            item { QuickActionSection(navController) }

            item {
                Text(
                    text = "Market Trends",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = CryptoWhite
                )
            }

            items(coinList.take(10)){ coin ->
                CoinListItem(
                    coin = coin,
                    onClick = { coinId ->
                        navController.navigate("coinDetailsScreen/$coinId")
                    }

                )
            }

        }
    }


}