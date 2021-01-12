package com.reach.philip.arnold.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reach.philip.arnold.model.Product
import com.reach.philip.arnold.repo.BasketRepository
import com.reach.philip.arnold.repo.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailsViewModel(
    private val basketRepo: BasketRepository,
    private val prodRepo: ProductRepository
) :
    ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val productItem = MutableLiveData<Product>()
    val basketCount = MutableLiveData<Int>()

    var productId: String = ""
        set(id: String) {
            field = id
            loadProduct()
            loadBasket()
        }

    private fun loadProduct() {
        launch {
            prodRepo.getProduct(productId, productItem)
        }
    }

    private fun loadBasket() {
        launch {
            basketRepo.getCount(productId, basketCount)
        }
    }

    fun addToBasket() {
        basketRepo.add(1, productId)
        val count = basketCount.value
        count?.let {
            basketCount.value = count.plus(1)
        } ?: run {
            basketCount.value = 1
        }
    }
}