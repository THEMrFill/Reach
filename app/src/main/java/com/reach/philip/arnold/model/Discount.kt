package com.reach.philip.arnold.model

open class Discount(
    val name: String? = null,
    val productId: String,
    val minQuantity: Int = 0,
    val exactQuantity: Int = 0,
    val nextFree: Boolean = false,
    val newPrice: Double = 0.0,
)
