package com.reach.philip.arnold.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.reach.philip.arnold.BuildConfig
import com.reach.philip.arnold.api.ApiNetwork
import com.reach.philip.arnold.repo.*
import com.reach.philip.arnold.ui.cart.CartViewModel
import com.reach.philip.arnold.ui.details.DetailsViewModel
import com.reach.philip.arnold.ui.main.MainViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules = module {
    // The Retrofit service using our custom HTTP client instance as a singleton
    single {
        createWebService(
            okHttpClient = createHttpClient(),
            baseUrl = BuildConfig.API_BASE_URL,
        )
    }
    // factories for Koin
    single<BasketRepository> { BasketRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
    single<StorageRepository> { StorageRepositoryImpl() }

    viewModel { MainViewModel(get(), get()) }
    viewModel { DetailsViewModel(get(), get()) }
    viewModel { CartViewModel(get(), get()) }
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(30, TimeUnit.SECONDS)
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method, original.body).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

/* function to build our Retrofit service */
fun createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String
): ApiNetwork {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
    return retrofit.create(ApiNetwork::class.java)
}