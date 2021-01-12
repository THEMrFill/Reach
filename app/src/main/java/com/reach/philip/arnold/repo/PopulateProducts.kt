package com.reach.philip.arnold.repo

import com.reach.philip.arnold.model.Product
import io.realm.RealmList

object PopulateProducts {
    fun makePoducts(): RealmList<Product> {
        val products = RealmList<Product>()
        /*
        val product1 = Product(
            id = "LIPSTICK",
            name = "Express Lipstick",
            price = 22.00,
            image = "https://i.imgur.com/Uk8dqpo.png",
        )
        products.add(product1)

        val product2 = Product(
            id = "EYELINER",
            name = "Daily Eyeliner",
            price = 50.00,
            image = "https://i.imgur.com/3bY9FTJ.png",
        )
        products.add(product2)

        val product3 = Product(
            id = "SHAMPOO",
            name = "RSVP Shampoo",
            price = 35.50,
            image = "https://i.imgur.com/X7fDF6w.png",
        )
        products.add(product3)
        */
        return products
    }
}