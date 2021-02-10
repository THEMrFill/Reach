package com.reach.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.reach.philip.arnold.api.ApiNetwork
import com.reach.philip.arnold.api.SingleLiveEvent
import com.reach.philip.arnold.api.UseCaseResult
import com.reach.philip.arnold.model.CartItem
import com.reach.philip.arnold.model.Product
import com.reach.philip.arnold.model.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(private val storageRepo: StorageRepository, val api: ApiNetwork): ProductRepository {
    override suspend fun retrieveProducts(): UseCaseResult<Products> {
        return try {
            val result = api.getProducts().await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun getProducts(liveData: MutableLiveData<Products>, error: SingleLiveEvent<String>, unit: () -> Unit) {
        loadProductsFromDb(liveData)
        when (val result = withContext(Dispatchers.IO) { retrieveProducts() }) {
            is UseCaseResult.Success -> {
                liveData.value = result.data
                storageRepo.saveProductList(result.data.products)
                unit()
            }
            is UseCaseResult.Error -> {

            }
        }
    }
    fun loadProductsFromDb(liveData: MutableLiveData<Products>) {
        val products = storageRepo.loadProductList()
        if (products.isEmpty()) {
            val newProducts = PopulateProducts.makePoducts()
            saveProducts(newProducts)
            liveData.value = Products(newProducts)
        } else {
            liveData.value = Products(products)
        }
    }

    override fun getProduct(id: String, liveData: MutableLiveData<Product>) {
        val product = storageRepo.loadProduct(id)
        liveData.value = product
    }

    override fun saveProducts(products: List<Product>) {
        val prods = ArrayList<Product>().apply {
            addAll(products)
        }
        storageRepo.saveProductList(prods)
    }

    override fun addToCart(cartItem: CartItem) {
        storageRepo
    }

    override fun removeFromCart(cartItem: CartItem) {
        TODO("Not yet implemented")
    }
}