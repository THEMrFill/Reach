package com.reach.philip.arnold.repo

import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.model.CartItem
import com.reach.philip.arnold.model.Discount
import com.reach.philip.arnold.model.Product
import io.realm.RealmList

interface StorageRepository {
    fun saveProductList(products: RealmList<Product>)
    fun loadProductList(): RealmList<Product>
    fun loadProduct(id: String): Product?

    fun saveCart(cart: Cart)
    fun loadCart(): Cart

    fun loadDiscounts(): List<Discount>
}