package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.AssetTransactionItem
import com.erguncoban.cryptoexchangeapp.components.TopBarTradeHistory
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AssetTransactionsViewModel

@Composable
fun AssetTransactionsScreen(navController: NavController, viewModel: AssetTransactionsViewModel = hiltViewModel()){

    val transactionList by viewModel.transactionList.collectAsState()

    Scaffold(
        topBar = {
            TopBarTradeHistory(navController, "Assets")
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {

            if (transactionList.isEmpty()){
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "No transactions found",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = CryptoGray
                        )
                    }
                }
            }else{
                items(
                    items = transactionList,
                    key = { trade -> trade.id }
                ){ tradeItem ->
                    AssetTransactionItem(
                        item = tradeItem,
                        onClick = {
                            navController.navigate("assetTransactionDetailScreen")
                        }
                    )

                }
            }

        }

    }

}