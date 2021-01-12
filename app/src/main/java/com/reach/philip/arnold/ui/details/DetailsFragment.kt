package com.reach.philip.arnold.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.reach.philip.arnold.R
import com.reach.philip.arnold.databinding.DetailsFragmentBinding
import com.reach.philip.arnold.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment: Fragment() {
    companion object {
        const val ID = "ID"

        fun newInstance(id: String): DetailsFragment {
            val bundle = Bundle()
            bundle.putString(ID, id)
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: DetailsViewModel by viewModel()
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsFragmentBinding.bind(view)

        val bundle = arguments
        bundle?.let {
            viewModel.productId = bundle.getString(ID, "")
        } ?: run {

        }

        setupObservers()
    }

    private fun setupObservers() {
        with (viewModel) {
            productItem.nonNullObserve(viewLifecycleOwner) { prod ->
                with (binding) {
                    product = prod
                    Glide.with(image)
                        .load(prod.image)
                        .into(image)
                    addToBasket.setOnClickListener {
                        clickAdd()
                    }
                }
            }
            basketCount.nonNullObserve(viewLifecycleOwner) { count ->
                binding.basket = count
                binding.basketCount.visibility = if (count > 0)
                    View.VISIBLE
                else
                    View.GONE
            }
        }
    }

    private fun clickAdd() {
        viewModel.addToBasket()
    }
}