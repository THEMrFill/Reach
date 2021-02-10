package com.reach.philip.arnold.model

import com.reach.philip.arnold.utils.NumberFormatter
import java.util.*

data class Product(
    var id: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var image: String? = null,
) {
    fun formatPrice(): String {
        return NumberFormatter.formatNumber(Locale.getDefault(), price)
    }
}
