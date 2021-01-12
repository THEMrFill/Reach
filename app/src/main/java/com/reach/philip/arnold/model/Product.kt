package com.reach.philip.arnold.model

import com.reach.philip.arnold.utils.NumberFormatter.formatNumber
import io.realm.RealmObject
import java.util.*

open class Product(
    var id: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var image: String? = null,
): RealmObject() {
    fun formatPrice(): String {
        return formatNumber(Locale.getDefault(), price)
    }
}
