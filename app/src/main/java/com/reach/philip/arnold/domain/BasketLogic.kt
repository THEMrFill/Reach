package com.reach.philip.arnold.domain

import com.reach.philip.arnold.model.*

object BasketLogic {
    fun calcTotal(discounts: List<Discount>,
                  products: ArrayList<Product>,
                  cart: Cart
    ): CartTotal {
        val total = CartTotal()
        for (cartItem in cart.cart) {
            val product = products.first { it.id == cartItem.product }
            val hasDiscount: Discount? = discounts.firstOrNull { it.productId == cartItem.product }
            val discountItem = discountItem(hasDiscount, cartItem, product)
            total.total += discountItem.total
            total.discounts.addAll(discountItem.discounts)
        }

        return total
    }

    fun discountItem(hasDiscount: Discount?, cartItem: CartItem, product: Product): CartTotal {
        val total = CartTotal()
        hasDiscount?.let { discount ->
            when {
                discount.nextFree &&
                        cartItem.quantity > discount.exactQuantity -> {
                    val remainder = cartItem.quantity.rem(discount.exactQuantity + 1)
                    val counter = cartItem.quantity.div(discount.exactQuantity + 1)
                    total.total = product.price * (remainder + counter + counter)
                    total.discounts.add(hasDiscount)
                }
                discount.exactQuantity > 0 &&
                        discount.newPrice > 0.0 &&
                        cartItem.quantity >= discount.exactQuantity -> {
                    total.total = product.price * discount.exactQuantity +
                            discount.newPrice * (cartItem.quantity - discount.exactQuantity)
                    total.discounts.add(hasDiscount)
                }
                discount.minQuantity > 0 &&
                        discount.newPrice > 0.0 &&
                        cartItem.quantity >= discount.minQuantity -> {
                    total.total = discount.newPrice * cartItem.quantity
                    total.discounts.add(hasDiscount)
                }
                else -> {
                    total.total = product.price * cartItem.quantity
                }
            }
        } ?: run {
            total.total = product.price * cartItem.quantity
        }
        return total
    }

}