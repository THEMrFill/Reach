package com.reach.philip.arnold.api

import com.reach.philip.arnold.model.Products
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiNetwork {
    @GET("products.json")
    fun getProducts(): Deferred<Products>
}