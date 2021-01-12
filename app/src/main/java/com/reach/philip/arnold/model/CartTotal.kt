package com.reach.philip.arnold.model

import com.reach.philip.arnold.utils.NumberFormatter
import java.util.*
import kotlin.collections.ArrayList

class CartTotal(
    var total: Double = 0.0,
    val discounts: ArrayList<Discount> = ArrayList(),
) {
    fun formatTotal(): String {
        return NumberFormatter.formatNumber(Locale.getDefault(), total)
    }
}