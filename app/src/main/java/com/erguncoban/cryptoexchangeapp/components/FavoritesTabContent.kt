package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.FavoritesViewModel

@Composable
fun FavoritesTabContent(navController: NavController, searchText: String, viewModel: FavoritesViewModel = hiltViewModel()){

    val favoriteCoins by viewModel.favoriteCoinDetails.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(favoriteCoins){ coin ->
            CoinListItem(
                coin = coin,
                onClick = { coinId ->
                    navController.navigate("CoinDetailsScreen/$coinId")
                }
            )
        }

    }

}