package com.erguncoban.cryptoexchangeapp.components

fun FormatPrice(value: Double?): String {
    return value?.let { String.format("%,.2f", it) } ?: "-"
}