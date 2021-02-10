package com.reach.philip.arnold.repo

import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.storage.CartItemStorage
import com.reach.philip.arnold.model.Discount
import com.reach.philip.arnold.model.Product
import com.reach.philip.arnold.storage.ProductStorage
import io.realm.Realm
import io.realm.RealmList

class StorageRepositoryImpl : StorageRepository {
    private val realm = Realm.getDefaultInstance()

    override fun saveProductList(products: ArrayList<Product>) {
        with(realm) {
            beginTransaction()
            for (prod in products) {
                val findProduct = where(ProductStorage::class.java).equalTo("id", prod.id)
                if (findProduct.count() > 0) {
                    findProduct.findFirst()?.update(prod)
                } else {
                    insertOrUpdate(ProductStorage(prod))
                }
            }
            val stored = where(ProductStorage::class.java).findAll()
            for (prod in stored.listIterator()) {
                if (!products.contains(prod.toProduct())) {
                    where(ProductStorage::class.java).equalTo("id", prod.id).findFirst()
                        ?.deleteFromRealm()
                }
            }
            commitTransaction()
        }
    }

    override fun loadProductList(): ArrayList<Product> {
        val foundProds = realm.where(ProductStorage::class.java).findAll()
        return ArrayList<Product>().apply {
            for (item in foundProds.toList()) {
                add(item.toProduct())
            }
        }
    }

    override fun loadProduct(id: String): Product? {
        val findProduct = realm.where(ProductStorage::class.java).equalTo("id", id).findFirst()
        return findProduct?.toProduct()
    }

    override fun saveCart(cart: Cart) {
        with(realm) {
            beginTransaction()
            for (item in cart.cart) {
                val result =
                    realm.where(CartItemStorage::class.java).equalTo("product", item.product)
                if (result.count() > 0) {
                    val found = result.findFirst()
                    if (item.quantity > 0) {
                        found?.quantity = item.quantity
                        insertOrUpdate(found)
                    } else {
                        found?.deleteFromRealm()
                    }
                } else {
                    insertOrUpdate(CartItemStorage(item))
                }
            }
            commitTransaction()
        }
    }

    private fun getRealmCart(): RealmList<CartItemStorage> {
        val results = realm.where(CartItemStorage::class.java).findAll()
        return if (results.isEmpty())
            RealmList()
        else {
            RealmList<CartItemStorage>().apply {
                addAll(results.subList(0, results.size))
            }
        }
    }

    override fun loadCart(): Cart {
        val cart = Cart()
        val cartItems = getRealmCart()
        for (item in cartItems) {
            cart.cart.add(item.toCartItem())
        }
        return cart
    }

    override fun loadDiscounts(): List<Discount> = PopulateDiscounts.populateDiscounts()
}