package com.reach.philip.arnold.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reach.philip.arnold.api.SingleLiveEvent
import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.model.Products
import com.reach.philip.arnold.repo.BasketRepository
import com.reach.philip.arnold.repo.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val basketRepo: BasketRepository,
    private val prodRepo: ProductRepository
) :
    ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData(true)
    val showError = SingleLiveEvent<String>()
    val productList = MutableLiveData<Products>()
    val basketList = MutableLiveData<Cart>()

    fun refreshData() {
        loadProducts()
        loadBasket()
    }

    private fun loadProducts() {
        productList.value = Products()
        showLoading.value = true
        launch {
            prodRepo.getProducts(productList, showError) {
                showLoading.value = false
            }
        }
    }

    private fun loadBasket() {
        basketList.value = Cart()
        launch {
            basketRepo.list(basketList) {}
        }
    }

    fun addToBasket(id: String) {
        basketRepo.add(1, id)
        loadBasket()
    }
}