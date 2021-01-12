package com.reach.philip.arnold.model

import io.realm.RealmObject

open class CartItem(
    var product: String = "",
    var quantity: Int = 0,
): RealmObject() {
    fun quantityOutput() : String =
        quantity.toString()
}
