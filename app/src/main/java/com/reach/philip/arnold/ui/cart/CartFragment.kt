package com.reach.philip.arnold.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.reach.philip.arnold.R
import com.reach.philip.arnold.databinding.CartFragmentBinding
import com.reach.philip.arnold.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class CartFragment: Fragment() {
    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModel()
    private lateinit var binding: CartFragmentBinding
    private val cartAdapter = CartAdapter(::addClick, ::removeClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CartFragmentBinding.bind(view)
        with (binding.recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            productList.nonNullObserve(viewLifecycleOwner) { prods ->
                prods?.let {
                    cartAdapter.setProducts(it)
                }
            }
            basketData.nonNullObserve(viewLifecycleOwner) { cart ->
                cart?.let {
                    cartAdapter.setBasket(it)
                }
            }
            cartTotal.nonNullObserve(viewLifecycleOwner) { total ->
                total?.let {
                    binding.cartTotal = it
                }
            }
        }
    }

    private fun addClick(id: String) {
        viewModel.addToBasket(id)
    }
    private fun removeClick(id: String) {
        viewModel.removeFromBasket(id)
    }
}