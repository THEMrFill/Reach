package com.reach.philip.arnold.repo

import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.model.Discount
import com.reach.philip.arnold.model.Product

interface StorageRepository {
    fun saveProductList(products: ArrayList<Product>)
    fun loadProductList(): ArrayList<Product>
    fun loadProduct(id: String): Product?

    fun saveCart(cart: Cart)
    fun loadCart(): Cart

    fun loadDiscounts(): List<Discount>
}