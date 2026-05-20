package com.app.minishop.core.utils

import android.icu.text.NumberFormat
import java.util.Locale

object CurrencyFormatterUtils {
    private const val RIEL_EXCHANGE_RATE = 4100

    fun formatToUSD(amount: Double): String {
        return try {
            NumberFormat.getCurrencyInstance(Locale.US).format(amount)
        } catch (e: Exception) {
            String.format("$%.2f", amount)
        }
    }

    fun formatToKHR(amountInUSD: Double): String {
        return try {
            val rielAmount = (amountInUSD * RIEL_EXCHANGE_RATE).toLong()
            val formatter = NumberFormat.getNumberInstance(Locale.US)
            "${formatter.format(rielAmount)} ៛"
        } catch (e: Exception) {
            "${(amountInUSD * RIEL_EXCHANGE_RATE).toInt()} ៛"
        }
    }
}