package com.reach.philip.arnold.storage

import com.reach.philip.arnold.model.Product
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ProductStorage(
    var id: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var image: String? = null,
): RealmObject() {
    constructor(product: Product): this(
        id = product.id,
        name = product.name,
        price = product.price,
        image = product.image,
    )

    fun toProduct() =
        Product(
            id = id,
            name = name,
            price = price,
            image = image,
        )

    fun update(product: Product) {
        id = product.id
        name = product.name
        price = product.price
        image = product.image
    }
}
