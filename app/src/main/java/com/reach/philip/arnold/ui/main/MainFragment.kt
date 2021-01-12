package com.reach.philip.arnold.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.reach.philip.arnold.ui.activity.MainActivity
import com.reach.philip.arnold.R
import com.reach.philip.arnold.databinding.MainFragmentBinding
import com.reach.philip.arnold.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModel()
    private val mainAdapter = MainAdapter(::clickRow, ::clickAdd)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        with (binding) {
            with(recycler) {
                layoutManager = LinearLayoutManager(context)
                adapter = mainAdapter
            }
            basketIcon.setOnClickListener {
                clickBasket()
            }
        }
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
    }

    private fun setupObservers() {
        with (viewModel) {
            productList.nonNullObserve(viewLifecycleOwner) {
                mainAdapter.setProducts(it)
            }
            basketList.nonNullObserve(viewLifecycleOwner) { cart ->
                cart?.let {
                    var counter = 0
                    cart.cart.forEach { prod ->
                        counter += prod.quantity
                    }
                    binding.basketCount.text = counter.toString()
                }
            }
            showLoading.nonNullObserve(viewLifecycleOwner) {
                with (binding.spinner) {
                    if (it)
                        show()
                    else
                        hide()
                }
            }
        }
    }

    private fun clickBasket() {
        (activity as MainActivity).navigateToCart()
    }
    private fun clickRow(id: String) {
        (activity as MainActivity).navigateToProduct(id)
    }
    private fun clickAdd(id: String) {
        viewModel.addToBasket(id)
    }
}