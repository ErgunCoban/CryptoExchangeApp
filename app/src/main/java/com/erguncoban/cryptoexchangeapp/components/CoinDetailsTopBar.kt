package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.data.entity.CryptoCoin
import com.erguncoban.cryptoexchangeapp.ui.theme.CryptoWhite
import com.erguncoban.cryptoexchangeapp.ui.theme.YellowTheme

@Composable
fun CoinDetailsTopBar(navController: NavController, coin: CryptoCoin?){

    var isFavorite by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Price", "Info")

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "back",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = coin?.name ?: "Loading..",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = CryptoWhite
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        isFavorite = !isFavorite
                    }
                ) {
                    Icon(
                        painter = if (isFavorite){
                            painterResource(R.drawable.filled_star_icon)
                        }else{
                            painterResource(R.drawable.star_icon)
                        },
                        contentDescription = "add favourite",
                        modifier = Modifier.size(20.dp),
                        tint = if (!isFavorite){
                            CryptoWhite
                        }else{
                            YellowTheme
                        }
                    )
                }

                IconButton(
                    onClick = {
                        //bildirimler sayfasına geçiş
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.notifications_icon),
                        contentDescription = "nofitications",
                        modifier = Modifier.size(24.dp),
                        tint = CryptoWhite
                    )
                }

            }

        }

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = CryptoWhite,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = YellowTheme
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index){
                                FontWeight.Bold
                            }else{
                                FontWeight.Normal
                            }
                        )
                    }
                )
            }
        }
    }

}