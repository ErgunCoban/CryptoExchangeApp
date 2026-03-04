package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.TopBarTradeHistory
import com.erguncoban.cryptoexchangeapp.components.TradeHistorySummaryItem
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.SpotTradeHistoryViewModel

@Composable
fun SpotTradeHistoryScreen(navController: NavController, viewModel: SpotTradeHistoryViewModel = hiltViewModel()){

    val tradeList by viewModel.tradeList.collectAsState()

    Scaffold(
        topBar = {
            TopBarTradeHistory(navController, "My Trades")
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (tradeList.isEmpty()){
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No trades found.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = CryptoGray
                        )
                    }
                }
            }else{
                items(
                    items = tradeList,
                    key = { trade -> trade.id}
                ){tradeItem ->
                    TradeHistorySummaryItem(
                        item = tradeItem,
                        onClick = { tradeId ->
                            navController.navigate("tradeDetailScreen")
                        }
                    )
                }
            }
        }

    }

}