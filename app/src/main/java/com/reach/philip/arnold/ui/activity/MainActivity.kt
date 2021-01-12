package com.reach.philip.arnold.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reach.philip.arnold.R
import com.reach.philip.arnold.ui.cart.CartFragment
import com.reach.philip.arnold.ui.details.DetailsFragment
import com.reach.philip.arnold.ui.main.MainFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    companion object {
        const val PRODUCT_DETAILS = "Product"
        const val CART_DETAILS = "Cart"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    fun navigateToProduct(id: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance(id))
            .addToBackStack(PRODUCT_DETAILS)
            .commit()
    }

    fun navigateToCart() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CartFragment.newInstance())
            .addToBackStack(CART_DETAILS)
            .commit()
    }
}