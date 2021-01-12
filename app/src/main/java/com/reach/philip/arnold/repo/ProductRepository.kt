package com.reach.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.reach.philip.arnold.api.SingleLiveEvent
import com.reach.philip.arnold.api.UseCaseResult
import com.reach.philip.arnold.model.CartItem
import com.reach.philip.arnold.model.Product
import com.reach.philip.arnold.model.Products

interface ProductRepository {
    suspend fun retrieveProducts(): UseCaseResult<Products>
    suspend fun getProducts(liveData: MutableLiveData<Products>, error: SingleLiveEvent<String>, unit: () -> Unit)
    fun getProduct(id: String, liveData: MutableLiveData<Product>)
    fun saveProducts(products: List<Product>)
    fun addToCart(cartItem: CartItem)
    fun removeFromCart(cartItem: CartItem)
}