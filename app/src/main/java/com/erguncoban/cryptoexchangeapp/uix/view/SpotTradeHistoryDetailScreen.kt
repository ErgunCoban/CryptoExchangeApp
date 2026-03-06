package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.TopBarTradeHistory
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoBlackBackground
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoYellow
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketGreen
import com.erguncoban.cryptoexchangeapp.ui.theme.MarketRed
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.SpotTradeHistoryDetailViewModel

@Composable
fun SpotTradeHistoryDetailScreen(navController: NavController, tradeId: String, viewModel: SpotTradeHistoryDetailViewModel = hiltViewModel()){

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = tradeId) {
        if (tradeId.isNotEmpty()){
            viewModel.getTradeDetail(tradeId)
        }
    }

    Scaffold(
        containerColor = CryptoBlackBackground,
        topBar = {
            TopBarTradeHistory(navController, "Trade Details")
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            if (state.isLoading){
                CircularProgressIndicator(color = CryptoYellow)
            }else if (state.errorMessage != null){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.errorMessage!!,
                        color = MarketRed,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.getTradeDetail(tradeId) },
                        colors = ButtonDefaults.buttonColors(containerColor = CryptoYellow)
                    ) {
                        Text("Retry", color = Color.Black)
                    }
                }
            }else if (state.trade != null){
                val trade = state.trade!!

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Veri Başarıyla Çekildi!",
                        color = MarketGreen,
                        fontSize = 20.sp
                    )
                    Divider(color = CryptoGray)

                    Text("ID: ${trade.documentId}", color = Color.White)
                    Text("Coin: ${trade.coinId}", color = Color.White)
                    Text("İşlem Tipi: ${trade.type}", color = Color.White)
                    Text("Miktar: ${trade.amount}", color = Color.White)
                    Text("Fiyat: $${trade.price}", color = Color.White)
                    Text("Timestamp: ${trade.timestamp}", color = Color.White)
                }
            }
        }

    }


}