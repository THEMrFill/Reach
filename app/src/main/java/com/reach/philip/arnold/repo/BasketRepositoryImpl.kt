package com.reach.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.reach.philip.arnold.domain.BasketLogic
import com.reach.philip.arnold.model.*

class BasketRepositoryImpl(val storageRepo: StorageRepository): BasketRepository {
    override fun getCount(productId: String, countData: MutableLiveData<Int>) {
        val cart = getCart()
        for (item in cart.cart) {
            if (item.product == productId) {
                countData.value = item.quantity
                return
            }
        }
        countData.value = 0
    }

    override fun add(quantity: Int, productId: String) {
        val cart = getCart()
        var itemAdded = false
        for (item in cart.cart) {
            if (item.product == productId) {
                item.quantity += quantity
                itemAdded = true
            }
        }
        if (!itemAdded) {
            cart.cart.add(CartItem(quantity = quantity, product = productId))
        }
        storageRepo.saveCart(cart)
    }

    override fun remove(quantity: Int, productId: String) {
        val cart = getCart()
        val newCartItems = ArrayList<CartItem>()
        for (item in cart.cart) {
            val newItem = CartItem(quantity = item.quantity, product = item.product)
            if (newItem.product == productId) {
                newItem.quantity -= quantity
            }
            if (newItem.quantity > 0) {
                newCartItems.add(newItem)
            }
        }
        storageRepo.saveCart(Cart(newCartItems))
    }

    override fun list(list: MutableLiveData<Cart>, unit: () -> Unit) {
        list.value = getCart()
        unit()
    }
    fun getCart(): Cart = storageRepo.loadCart()

    override fun calculateTotal(total: MutableLiveData<CartTotal>) {
        total.value = calcTotal()
    }

    fun calcTotal() = BasketLogic.calcTotal(
        discounts = storageRepo.loadDiscounts(),
        products = storageRepo.loadProductList(),
        cart = getCart()
    )
}