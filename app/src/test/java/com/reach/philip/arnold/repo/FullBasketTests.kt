package com.reach.philip.arnold.repo

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.model.CartItem
import com.reach.philip.arnold.model.Discount
import com.reach.philip.arnold.model.Product
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito

class FullBasketTests {
    private val discounts = ArrayList<Discount>()
    private val products = ArrayList<Product>()
    init {
        discounts.add(
            Discount(
                productId = "LIPSTICK",
                minQuantity = 0,
                exactQuantity = 2,
                nextFree = true,
                newPrice = 0.0,
            )
        )
        discounts.add(
            Discount(
                productId = "EYELINER",
                minQuantity = 3,
                exactQuantity = 0,
                nextFree = false,
                newPrice = 30.0,
            )
        )
        products.add(
            Product(
                id = "LIPSTICK",
                name = "Lipstick",
                price = 22.0,
            )
        )
        products.add(
            Product(
                id = "EYELINER",
                name = "Eyeliner",
                price = 50.0,
            )
        )
        products.add(
            Product(
                id = "SHAMPOO",
                name = "Shampoo",
                price = 35.50,
            )
        )
    }

    private val storage = mock<StorageRepository>() {
        on { loadDiscounts() } doReturn discounts
        on { loadProductList() } doReturn products
    }
    private val repo = BasketRepositoryImpl(storage)

    @Test
    fun `check small basket`() {
        val cartItems = ArrayList<CartItem>()
        cartItems.add(CartItem("SHAMPOO", 2))
        Mockito.`when`(storage.loadCart()).thenReturn(Cart(cartItems))
        // 35.5 * 2
        val total = repo.calcTotal()
        assertEquals(71.0, total.total)
        assertEquals(0, total.discounts.size)
    }

    @Test
    fun `large number of items without a discount`() {
        val cartItems = ArrayList<CartItem>()
        cartItems.add(CartItem("SHAMPOO", 20))
        Mockito.`when`(storage.loadCart()).thenReturn(Cart(cartItems))
        // 35.5 * 20
        val total = repo.calcTotal()
        assertEquals(710.0, total.total)
        assertEquals(0, total.discounts.size)
    }

    @Test
    fun `two lipsticks and a shampoo`() {
        val cartItems = ArrayList<CartItem>()
        cartItems.add(CartItem("LIPSTICK", 2))
        cartItems.add(CartItem("SHAMPOO", 1))
        Mockito.`when`(storage.loadCart()).thenReturn(Cart(cartItems))
        // 22 * 2 + 35.5
        val total = repo.calcTotal()
        assertEquals(79.5, total.total)
        assertEquals(0, total.discounts.size)
    }

    @Test
    fun `two lipsticks and an eyeliner`() {
        val cartItems = ArrayList<CartItem>()
        cartItems.add(CartItem("LIPSTICK", 2))
        cartItems.add(CartItem("EYELINER", 1))
        Mockito.`when`(storage.loadCart()).thenReturn(Cart(cartItems))
        // 22 * 2 + 50
        val total = repo.calcTotal()
        assertEquals(94.0, total.total)
        assertEquals(0, total.discounts.size)
    }

    @Test
    fun `one lipstick and four eyeliners`() {
        val cartItems = ArrayList<CartItem>()
        cartItems.add(CartItem("LIPSTICK", 1))
        cartItems.add(CartItem("EYELINER", 4))
        Mockito.`when`(storage.loadCart()).thenReturn(Cart(cartItems))
        // 22 + 30 * 4, 1 discount
        val total = repo.calcTotal()
        assertEquals(142.0, total.total)
        assertEquals(1, total.discounts.size)
    }

    @Test
    fun `three lipsticks, three eyeliners and one shampoo`() {
        val cartItems = ArrayList<CartItem>()
        cartItems.add(CartItem("LIPSTICK", 3))
        cartItems.add(CartItem("SHAMPOO", 1))
        cartItems.add(CartItem("EYELINER", 3))
        Mockito.`when`(storage.loadCart()).thenReturn(Cart(cartItems))
        // 22 * 2 (one free) + 35.5 + 30 * 3, 2 discounts
        val total = repo.calcTotal()
        assertEquals(169.5, total.total)
        assertEquals(2, total.discounts.size)
    }
}