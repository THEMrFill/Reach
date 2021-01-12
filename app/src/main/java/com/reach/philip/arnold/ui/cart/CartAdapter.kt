package com.reach.philip.arnold.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reach.philip.arnold.databinding.CartItemBinding
import com.reach.philip.arnold.model.Cart
import com.reach.philip.arnold.model.CartItem
import com.reach.philip.arnold.model.Product
import com.reach.philip.arnold.model.Products
import io.realm.RealmList

class CartAdapter(
    private val addClick: (id: String) -> Unit,
    private val removeClick: (id: String) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    private val products = RealmList<Product>()
    private val basketList = RealmList<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, addClick, removeClick)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = basketList[position]
        item?.let { cartItem ->
            holder.bind(
                prod = products.find { it.id == cartItem.product },
                item = cartItem
            )
        }
    }

    override fun getItemCount(): Int = basketList.size

    fun setProducts(prods: Products) {
        with(products) {
            clear()
            addAll(prods.products)
        }
        notifyDataSetChanged()
    }

    fun setBasket(cart: Cart) {
        with(basketList) {
            clear()
            addAll(cart.cart)
        }
        notifyDataSetChanged()
    }

}

class CartViewHolder(
    private val binder: CartItemBinding,
    private val addClick: (id: String) -> Unit,
    private val removeClick: (id: String) -> Unit,
) : RecyclerView.ViewHolder(binder.root) {
    fun bind(prod: Product?, item: CartItem) {
        prod?.let { prod ->
            with (binder) {
                product = prod
                cartItem = item
                plus.setOnClickListener {
                    addClick(prod.id)
                }
                minus.setOnClickListener {
                    removeClick(prod.id)
                }
            }
        }
    }
}