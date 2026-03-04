package com.erguncoban.cryptoexchangeapp.components

import androidx.compose.runtime.Composable
import com.erguncoban.cryptoexchangeapp.data.entity.TradeHistory
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun TransactionItem(transaction: TradeHistory){

    val symbols = DecimalFormatSymbols(Locale("tr", "TR"))
    val usdFormatter = DecimalFormat("#,##0.00", symbols)
    val coinFormatter = DecimalFormat("#,##0.#####", symbols)



}