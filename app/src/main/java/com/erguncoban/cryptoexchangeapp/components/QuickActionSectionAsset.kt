package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.erguncoban.cryptoexchangeapp.R

@Composable
fun QuickActionSectionAsset(navController: NavController, onHistoryClick: () -> Unit){

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        QuickActionButton(R.drawable.deposit_icon, "Deposit", onClick = {
            navController.navigate("depositScreen")
        })
        QuickActionButton(R.drawable.withdraw_icon, "Withdraw", onClick = {
            navController.navigate("withdrawScreen")
        })
        QuickActionButton(R.drawable.transfer_icon, "Transfer", onClick = {
            navController.navigate("transferScreen")
        })
        QuickActionButton(R.drawable.activity_history, "History", onClick = {
            onHistoryClick()
        })

    }

}