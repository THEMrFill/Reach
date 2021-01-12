package com.reach.philip.arnold.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reach.philip.arnold.api.SingleLiveEvent
import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.model.CartTotal
import com.reach.philip.arnold.model.Products
import com.reach.philip.arnold.repo.BasketRepository
import com.reach.philip.arnold.repo.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CartViewModel(
    private val basketRepo: BasketRepository,
    private val prodRepo: ProductRepository
): ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val basketData = MutableLiveData<Cart>()
    val productList = MutableLiveData<Products>()
    val error = SingleLiveEvent<String>()
    val cartTotal = MutableLiveData<CartTotal>()

    init {
        loadBasket()
        loadProducts()
    }

    private fun loadBasket() {
        launch {
            basketRepo.list(basketData) {
                checkBothAndMakeTotal()
            }
        }
    }

    private fun loadProducts() {
        launch {
            prodRepo.getProducts(productList, error) {
                checkBothAndMakeTotal()
            }
        }
    }

    private fun checkBothAndMakeTotal() {
        if (basketData.value != null && productList.value != null) {
            basketRepo.calculateTotal(cartTotal)
        }
    }

    fun addToBasket(id: String) {
        basketRepo.add(1, id)
        loadBasket()
    }
    fun removeFromBasket(id: String) {
        basketRepo.remove(1, id)
        loadBasket()
    }
}