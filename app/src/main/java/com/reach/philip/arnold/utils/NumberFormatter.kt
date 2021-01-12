package com.reach.philip.arnold.utils

import java.text.NumberFormat
import java.util.*

object NumberFormatter {
    fun formatNumber(locale: Locale, value: Double): String {
        val format = NumberFormat.getCurrencyInstance(locale).apply {
            currency = Currency.getInstance(locale)
        }
        return format.format(value)
    }
}