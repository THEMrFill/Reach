package com.reach.philip.arnold.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reach.philip.arnold.databinding.ProductItemBinding
import com.reach.philip.arnold.model.Product
import com.reach.philip.arnold.model.Products

class MainAdapter(
    private val rowClick: (id: String) -> Unit,
    private val addClick: (id: String) -> Unit,
) :
    RecyclerView.Adapter<ProductViewHolder>() {
    private val products: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, rowClick, addClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun setProducts(prods: Products) {
        with(products) {
            clear()
            addAll(prods.products)
        }
        notifyDataSetChanged()
    }
}

class ProductViewHolder(
    private val binder: ProductItemBinding,
    private val rowClick: (id: String) -> Unit,
    private val addClick: (id: String) -> Unit
) : RecyclerView.ViewHolder(binder.root) {

    fun bind(prod: Product) {
        with(binder) {
            product = prod
            root.setOnClickListener {
                rowClick(prod.id)
            }
            add.setOnClickListener {
                addClick(prod.id)
            }
        }
    }
}