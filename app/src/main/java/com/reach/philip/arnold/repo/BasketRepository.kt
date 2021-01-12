package com.reach.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.model.CartTotal

interface BasketRepository {
    fun getCount(productId: String, countData: MutableLiveData<Int>)
    fun add(quantity: Int, productId: String)
    fun remove(quantity: Int, productId: String)
    fun list(list: MutableLiveData<Cart>, unit: () -> Unit)
    fun calculateTotal(total: MutableLiveData<CartTotal>)
}