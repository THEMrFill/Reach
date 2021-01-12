package com.reach.philip.arnold.model

import io.realm.RealmList
import io.realm.RealmObject

open class Cart(
   var cart: RealmList<CartItem> = RealmList()
): RealmObject()