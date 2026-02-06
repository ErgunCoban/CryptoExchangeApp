package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.components.HomeTopBar
import com.erguncoban.cryptoexchangeapp.components.PortfolioCard
import com.erguncoban.cryptoexchangeapp.components.QuickActionSection
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoBlackBackground
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){

    Scaffold(
        containerColor = CryptoBlackBackground,
        topBar = {
            HomeTopBar(navController)
        }
    ) {
        paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            item { PortfolioCard() }

            item { QuickActionSection() }

            item {
                Text(
                    text = "Market Trends",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = CryptoWhite
                )
            }

        }
    }


}