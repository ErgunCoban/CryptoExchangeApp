package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.MarketsViewModel

@Composable
fun MarketsTabContent(
    navController: NavController,
    searchText: String,
    viewModel: MarketsViewModel = hiltViewModel()
) {

    val coinList by viewModel.coinList.collectAsState(initial = emptyList())

    val filteredList = coinList.filter {
        it.name.contains(searchText, ignoreCase = true)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(filteredList) { coin ->
            CoinListItem(
                coin = coin,
                onClick = { coinId ->
                    navController.navigate("coinDetailsScreen/$coinId")
                }
            )
        }
    }
}