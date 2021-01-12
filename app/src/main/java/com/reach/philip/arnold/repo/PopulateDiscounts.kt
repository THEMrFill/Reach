package com.reach.philip.arnold.repo

import com.reach.philip.arnold.model.Discount

object PopulateDiscounts {
    fun populateDiscounts(): List<Discount> {
        val discounts = ArrayList<Discount>()

        val discount1 = Discount(
            name = "Buy two lipsticks, get one free",
            productId = "LIPSTICK",
            minQuantity = 0,
            exactQuantity = 2,
            nextFree = true,
            newPrice = 0.0,
        )
        discounts.add(discount1)

        val discount2 = Discount(
            name = "Buy 3 eyeliners, get them at 30 €",
            productId = "EYELINER",
            minQuantity = 3,
            exactQuantity = 0,
            nextFree = false,
            newPrice = 30.0,
        )
        discounts.add(discount2)

        return discounts
    }
}