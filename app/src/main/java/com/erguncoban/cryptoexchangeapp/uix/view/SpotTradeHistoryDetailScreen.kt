package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.erguncoban.cryptoexchangeapp.components.TradeDetailRow
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoBlackBackground
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
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
                .padding(16.dp)
        ){
            if (state.isLoading){
                CircularProgressIndicator(
                    color = CryptoYellow,
                    modifier = Modifier.align(Alignment.Center)
                )
            }else if (state.errorMessage != null){
                Column(
                    modifier = Modifier.align(Alignment.Center),
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

                val typeColor = if (trade.type.uppercase() == "BUY") MarketGreen else MarketRed

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "${trade.amount} ${trade.coinId.uppercase()}",
                        color = CryptoWhite,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            TradeDetailRow(label = "Trade ID", value = trade.documentId)
                            Divider(color = Color(0xFF2C2C2C), modifier = Modifier.padding(vertical = 12.dp))

                            TradeDetailRow(label = "Trade Type", value = trade.type, valueColor = typeColor)
                            Divider(color = Color(0xFF2C2C2C), modifier = Modifier.padding(vertical = 12.dp))

                            TradeDetailRow(label = "Price", value = "$${trade.price}")
                            Divider(color = Color(0xFF2C2C2C), modifier = Modifier.padding(vertical = 12.dp))

                            TradeDetailRow(label = "Time", value = state.formattedDate)
                        }
                    }
                }
            }
        }

    }

}