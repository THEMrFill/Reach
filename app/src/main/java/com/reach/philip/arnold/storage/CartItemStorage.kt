package com.reach.philip.arnold.storage

import com.reach.philip.arnold.model.CartItem
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CartItemStorage(
    var product: String = "",
    var quantity: Int = 0,
): RealmObject() {
    constructor(cartItem: CartItem): this(cartItem.product, cartItem.quantity)

    fun toCartItem(): CartItem = CartItem(product, quantity)
}
