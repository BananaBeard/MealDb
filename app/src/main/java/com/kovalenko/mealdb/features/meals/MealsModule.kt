package com.kovalenko.mealdb.features.meals

import android.app.Application
import androidx.room.Room
import com.kovalenko.mealdb.features.meals.model.database.MealDao
import com.kovalenko.mealdb.features.meals.model.database.MealDatabase
import com.kovalenko.mealdb.features.meals.model.network.MealService
import com.kovalenko.mealdb.features.meals.model.repository.MealRepository
import com.kovalenko.mealdb.features.meals.viewmodel.MealDetailViewModel
import com.kovalenko.mealdb.features.meals.viewmodel.MealsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mealsModule = module {
    fun provideMealsApi(retrofit: Retrofit) =
        retrofit.create(MealService::class.java)

    fun provideDatabase(application: Application) =
        Room.databaseBuilder(
            application,
            MealDatabase::class.java,
            "meals"
        ).build()

    fun provideDao(database: MealDatabase) = database.mealDao

    fun provideRepository(api: MealService, dao: MealDao) = MealRepository(api, dao)

    single { provideMealsApi(get()) }
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
    single { provideRepository(get(), get()) }
    viewModel { MealsViewModel(get()) }
    viewModel { MealDetailViewModel(get()) }
}
