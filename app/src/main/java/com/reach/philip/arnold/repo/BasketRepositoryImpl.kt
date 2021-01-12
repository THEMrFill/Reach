package com.reach.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.reach.philip.arnold.domain.BasketLogic
import com.reach.philip.arnold.model.*
import io.realm.RealmList

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
        val newCartItems = RealmList<CartItem>()
        var itemAdded = false
        for (item in cart.cart) {
            val newItem = CartItem(quantity = item.quantity, product = item.product)
            if (newItem.product == productId) {
                newItem.quantity += quantity
                itemAdded = true
            }
            newCartItems.add(newItem)
        }
        if (!itemAdded) {
            newCartItems.add(CartItem(quantity = quantity, product = productId))
        }
        storageRepo.saveCart(Cart(newCartItems))
    }

    override fun remove(quantity: Int, productId: String) {
        val cart = getCart()
        val newCartItems = RealmList<CartItem>()
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