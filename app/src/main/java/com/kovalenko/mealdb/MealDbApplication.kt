package com.kovalenko.mealdb

import android.app.Application
import com.kovalenko.mealdb.features.meals.mealsModule
import com.kovalenko.mealdb.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class MealDbApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MealDbApplication)
            modules(listOf(networkModule, mealsModule))
        }
    }
}
