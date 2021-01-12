package com.reach.philip.arnold.model

import io.realm.RealmList
import io.realm.RealmObject

open class Products(
    var products: RealmList<Product> = RealmList(),
): RealmObject()