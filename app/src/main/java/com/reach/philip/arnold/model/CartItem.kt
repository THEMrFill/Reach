package com.reach.philip.arnold.model

data class CartItem(
    var product: String = "",
    var quantity: Int = 0,
) {
    fun quantityOutput() : String =
        quantity.toString()
}
