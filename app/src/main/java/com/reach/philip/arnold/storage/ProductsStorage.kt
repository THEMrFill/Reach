package com.reach.philip.arnold.storage

import io.realm.RealmList
import io.realm.RealmObject

open class ProductsStorage(
    var products: RealmList<ProductStorage> = RealmList(),
): RealmObject()