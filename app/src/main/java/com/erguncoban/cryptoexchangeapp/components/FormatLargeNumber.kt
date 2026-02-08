package com.erguncoban.cryptoexchangeapp.components

fun FormatLargeNumber(value: Double?): String {
    if (value == null) return "-"

    return when {
        value >= 1_000_000_000 -> String.format("%,.2fB", value / 1_000_000_000)
        value >= 1_000_000 -> String.format("%,.2fM", value / 1_000_000)
        else -> String.format("%,.2f", value)
    }
}