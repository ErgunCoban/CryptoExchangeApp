package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erguncoban.cryptoexchangeapp.R

@Composable
fun QuickActionSection(){

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        QuickActionButton(R.drawable.deposit_icon, "Deposit")
        QuickActionButton(R.drawable.withdraw_icon, "Withdraw")
        QuickActionButton(R.drawable.transfer_icon, "Transfer")
        QuickActionButton(R.drawable.bitcoin_icon, "Buy Crypto", true)

    }

}