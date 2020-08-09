package com.kovalenko.mealdb.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kovalenko.mealdb.features.meals.model.network.MealDeserializer
import com.kovalenko.mealdb.features.meals.model.network.NetworkMeal
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    fun provideGson() = GsonBuilder()
        .registerTypeAdapter(NetworkMeal::class.java,
            MealDeserializer()
        )
        .create()

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}
