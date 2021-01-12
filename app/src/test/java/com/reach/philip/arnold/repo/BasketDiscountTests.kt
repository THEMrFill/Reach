package com.reach.philip.arnold.repo

import com.reach.philip.arnold.domain.BasketLogic
import com.reach.philip.arnold.model.CartItem
import com.reach.philip.arnold.model.Discount
import com.reach.philip.arnold.model.Product
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BasketDiscountTests {
    @Test
    fun `check simple item on basket`() {
        val discount: Discount? = null
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 1,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // no available discounts, so default price
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(10.0, total.total)
        assertEquals(0, total.discounts.size)
    }

    @Test
    fun `check discounted rate item on basket not enough quantity`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 3,
            exactQuantity = 0,
            nextFree = false,
            newPrice = 7.0,
            name = "",
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 1,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // doesn't meet the qty of 3, items at normal price of 10.0
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(10.0, total.total)
        assertEquals(0, total.discounts.size)
    }

    @Test
    fun `check discounted rate item on basket enough quantity`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 3,
            exactQuantity = 0,
            nextFree = false,
            newPrice = 7.0,
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 3,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // meets the qty of 3, items at lower price of 7.0
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(21.0, total.total)
        assertEquals(1, total.discounts.size)
    }

    @Test
    fun `check discounted rate item on basket many`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 3,
            exactQuantity = 0,
            nextFree = false,
            newPrice = 7.0,
            name = "",
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 10,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // exceeds the qty of 3, so 10 items at lower price of 7.0
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(70.0, total.total)
        assertEquals(1, total.discounts.size)
    }

    @Test
    fun `check discounted rate item after total basket many`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 0,
            exactQuantity = 3,
            nextFree = false,
            newPrice = 7.0,
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 10,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // exceeds the exact qty of 3, so 3 at normal price & 7 items at lower price of 7.0
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(79.0, total.total)
        assertEquals(1, total.discounts.size)
    }

    @Test
    fun `check free item on basket not enough quantity`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 0,
            exactQuantity = 2,
            nextFree = true,
            newPrice = 0.0,
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 1,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // doesn't meet the "buy 2 get 1 free"
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(10.0, total.total)
        assertEquals(0, total.discounts.size)
    }

    @Test
    fun `check free item on basket exact quantity`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 0,
            exactQuantity = 2,
            nextFree = true,
            newPrice = 0.0,
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 2,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // meets the "buy 2" but doesn't get any free (therefore no discount)
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(20.0, total.total)
        assertEquals(0, total.discounts.size)
    }

    @Test
    fun `check free item on basket one free`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 0,
            exactQuantity = 2,
            nextFree = true,
            newPrice = 0.0,
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 3,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // 1 lot of "buy 2 get 1 free"
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(20.0, total.total)
        assertEquals(1, total.discounts.size)
    }

    @Test
    fun `check free item on basket one free one extra`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 0,
            exactQuantity = 2,
            nextFree = true,
            newPrice = 0.0,
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 4,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )
        // 1 lot of "buy 2 get 1 free" and one extra
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(30.0, total.total)
        assertEquals(1, total.discounts.size)
    }

    @Test
    fun `check free item on basket many free one extra`() {
        val discount = Discount(
            productId = "ITEM",
            minQuantity = 0,
            exactQuantity = 2,
            nextFree = true,
            newPrice = 0.0,
        )
        val cartItem = CartItem(
            product = "ITEM",
            quantity = 8,
        )
        val product = Product(
            id = "ITEM",
            name = "name",
            price = 10.0,
        )

        // 2 lots of "buy 2 get 1 free" plus 2 extra items
        val total = BasketLogic.discountItem(discount, cartItem, product)
        assertEquals(60.0, total.total)
        assertEquals(1, total.discounts.size)
    }
}