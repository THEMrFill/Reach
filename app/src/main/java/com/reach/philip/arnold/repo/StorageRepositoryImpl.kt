package com.reach.philip.arnold.repo

import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.model.CartItem
import com.reach.philip.arnold.model.Discount
import com.reach.philip.arnold.model.Product
import io.realm.Realm
import io.realm.RealmList

class StorageRepositoryImpl: StorageRepository {
    private val realm = Realm.getDefaultInstance()

    override fun saveProductList(products: RealmList<Product>) {
        val prods = RealmList<Product>().apply {
            addAll(products)
        }
        with (realm) {
            beginTransaction()
            delete(Product::class.java)
            copyToRealm(prods)
            commitTransaction()
        }
    }

    override fun loadProductList(): RealmList<Product> {
        val foundProds = realm.where(Product::class.java).findAll()
        return RealmList<Product>().apply {
            addAll(foundProds.toList())
        }
    }

    override fun loadProduct(id: String): Product? {
        return realm.where(Product::class.java).equalTo("id", id).findFirst()
    }

    override fun saveCart(cart: Cart) {
        val items = RealmList<CartItem>().apply {
            addAll(cart.cart)
        }
        with (realm) {
            beginTransaction()
            delete(CartItem::class.java)
            copyToRealm(items)
            commitTransaction()
        }
    }

    override fun loadCart(): Cart {
        val results = realm.where(CartItem::class.java).findAll()
        if (results.isEmpty())
            return Cart(RealmList())
        else {
            val newItems = RealmList<CartItem>().apply {
                addAll(results.toList())
            }
            return Cart(newItems)
        }
    }

    override fun loadDiscounts(): List<Discount> = PopulateDiscounts.populateDiscounts()
}