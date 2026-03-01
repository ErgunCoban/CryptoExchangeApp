package com.erguncoban.cryptoexchangeapp.uix.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erguncoban.cryptoexchangeapp.R
import com.erguncoban.cryptoexchangeapp.ui.theme.TextGray
import com.erguncoban.cryptoexchangeapp.uix.view.AssetTransactionsScreen
import com.erguncoban.cryptoexchangeapp.uix.view.AssetsScreen
import com.erguncoban.cryptoexchangeapp.uix.view.CoinDetailsScreen
import com.erguncoban.cryptoexchangeapp.uix.view.DepositScreen
import com.erguncoban.cryptoexchangeapp.uix.view.HomeScreen
import com.erguncoban.cryptoexchangeapp.uix.view.LoginScreen
import com.erguncoban.cryptoexchangeapp.uix.view.MarketsScreen
import com.erguncoban.cryptoexchangeapp.uix.view.NotificationsScreen
import com.erguncoban.cryptoexchangeapp.uix.view.SignupScreen
import com.erguncoban.cryptoexchangeapp.uix.view.SpotTradeHistoryScreen
import com.erguncoban.cryptoexchangeapp.uix.view.TradeScreen
import com.erguncoban.cryptoexchangeapp.uix.view.TransferScreen
import com.erguncoban.cryptoexchangeapp.uix.view.UserProfileScreen
import com.erguncoban.cryptoexchangeapp.uix.view.WelcomeScreen
import com.erguncoban.cryptoexchangeapp.uix.view.WithdrawScreen

@Composable
fun BottomBar(startDestination: String){

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarScreens = listOf("homeScreen", "marketsScreen", "tradeScreen", "assetsScreen")

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarScreens){
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "homeScreen",
                        onClick = {navController.navigate("homeScreen")},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.bit_bridge_logo),
                                contentDescription = "home page",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Home",
                                color = TextGray
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "marketsScreen",
                        onClick = {navController.navigate("marketsScreen")},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.markets_icon),
                                contentDescription = "markets page"
                            )
                        },
                        label = {
                            Text(
                                text = "Markets",
                                color = TextGray
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "tradeScreen",
                        onClick = {navController.navigate("tradeScreen")},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.trade_icon),
                                contentDescription = "trade page"
                            )
                        },
                        label = {
                            Text(
                                text = "Trade",
                                color = TextGray
                            )
                        }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "assetsScreen",
                        onClick = {navController.navigate("assetsScreen")},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.wallet_icon),
                                contentDescription = "assets page"
                            )
                        },
                        label = {
                            Text(
                                text = "Assets",
                                color = TextGray
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ){

            composable("welcomeScreen"){
                WelcomeScreen(navController = navController)
            }

            composable("loginScreen"){
                LoginScreen(navController = navController)
            }

            composable("signupScreen"){
                SignupScreen(navController = navController)
            }

            composable("homeScreen"){
                HomeScreen(navController = navController)
            }

            composable("marketsScreen"){
                MarketsScreen(navController = navController)
            }

            composable("tradeScreen"){
                TradeScreen(navController = navController){isBuy, price, amount ->
                    println("Is Buy: $isBuy - Price: $price - Amount: $amount")
                }
            }

            composable("assetsScreen"){
                AssetsScreen(navController = navController)
            }

            composable("userProfileScreen"){
                UserProfileScreen(navController = navController)
            }

            composable("coinDetailsScreen/{coinId}",
                arguments = listOf(
                    navArgument("coinId"){ type = NavType.StringType}
                )
                ){ backStackEntry ->
                val coinId = backStackEntry.arguments?.getString("coinId") ?: ""
                CoinDetailsScreen(navController = navController, coinId = coinId)
            }

            composable("depositScreen"){
                DepositScreen(navController = navController)
            }

            composable("withdrawScreen"){
                WithdrawScreen(navController = navController)
            }

            composable("transferScreen"){
                TransferScreen(navController = navController){recipientAddress, assetType, amount ->
                    println("Recipient Address: $recipientAddress - Asset Type: $assetType - Transferred Amount: $amount")
                }
            }

            composable("notificationsScreen"){
                NotificationsScreen(navController = navController)
            }

            composable("assetTransactionsScreen"){
                AssetTransactionsScreen(navController = navController)
            }

            composable("spotTradeHistoryScreen"){
                SpotTradeHistoryScreen(navController = navController)
            }

        }

    }

}