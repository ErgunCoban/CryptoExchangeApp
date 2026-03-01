package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.TopBarTradeHistory

@Composable
fun AssetTransactionsScreen(navController: NavController){

    Scaffold(
        topBar = {
            TopBarTradeHistory(navController, "Assets")
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {  }

    }

}