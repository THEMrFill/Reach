package com.reach.philip.arnold.storage

import io.realm.RealmList
import io.realm.RealmObject

open class CartStorage(
   var cart: RealmList<CartItemStorage> = RealmList()
): RealmObject()