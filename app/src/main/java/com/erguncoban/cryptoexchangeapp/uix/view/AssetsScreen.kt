package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.components.AssetRowItem
import com.erguncoban.cryptoexchangeapp.components.PortfolioAssetCard
import com.erguncoban.cryptoexchangeapp.components.QuickActionSectionAsset
import com.erguncoban.cryptoexchangeapp.components.TopBarAsset
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoGray
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AssetsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen(navController: NavController, assetsViewModel: AssetsViewModel = hiltViewModel()){

    val totalBalance by assetsViewModel.totalPortfolioValue.collectAsState()

    val btcEquivalent by assetsViewModel.btcEquivalent.collectAsState()

    val assetList by assetsViewModel.assetUiList.collectAsState()

    var showHistorySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(totalBalance) {
        assetsViewModel.calculateBtcEquivalent(totalBalance)
    }

    Scaffold(
        topBar = {
            TopBarAsset()
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                PortfolioAssetCard(totalBalance, btcEquivalent)
                Spacer(modifier = Modifier.size(16.dp))
            }

            item {
                QuickActionSectionAsset(navController, onHistoryClick = {showHistorySheet = true})
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = "Your Assets",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = CryptoWhite
                    )
                }
            }

            items(assetList){ asset ->
                AssetRowItem(asset = asset)
            }

        }

    }

    if (showHistorySheet){
        ModalBottomSheet(
            onDismissRequest = { showHistorySheet = false },
            sheetState = sheetState,
            containerColor = Color(0xFF1E1E1E),
            contentColor = CryptoWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Select History",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = CryptoWhite,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)

                HistoryOptionRow("Assets", "Deposit, Withdraw, Transfer",
                    onClick = {showHistorySheet = false
                        navController.navigate("AssetTransactionsScreen")})

                HistoryOptionRow("My Trades", "Spot Buy & Sell",
                    onClick = {showHistorySheet = false
                        navController.navigate("SpotTradeHistoryScreen")})

            }
        }
    }

}

@Composable
fun HistoryOptionRow(title: String, subtitle: String, onClick: () -> Unit){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ onClick() }
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = CryptoWhite
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = CryptoGray
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Go",
            tint = CryptoGray
        )


    }

}