package com.erguncoban.cryptoexchangeapp.uix.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.components.AssetRowItem
import com.erguncoban.cryptoexchangeapp.components.PortfolioAssetCard
import com.erguncoban.cryptoexchangeapp.components.QuickActionButton
import com.erguncoban.cryptoexchangeapp.components.TopBarAsset
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.uix.viewmodel.AssetsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen(navController: NavController, assetsViewModel: AssetsViewModel = hiltViewModel()){

    val totalBalance by assetsViewModel.totalPortfolioValue.collectAsState()

    val btcEquivalent by assetsViewModel.btcEquivalent.collectAsState()

    val assetList by assetsViewModel.assetUiList.collectAsState()

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    QuickActionButton(
                        icon = R.drawable.deposit_icon,
                        label = "Deposit",
                        onClick = {
                            navController.navigate("depositScreen")
                        }
                    )

                    QuickActionButton(
                        icon = R.drawable.withdraw_icon,
                        label = "Withdraw",
                        onClick = {
                            navController.navigate("withdrawScreen")
                        }
                    )

                    QuickActionButton(
                        icon = R.drawable.transfer_icon,
                        label = "Transfer",
                        onClick = {
                            navController.navigate("transferScreen")
                        }
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
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

}