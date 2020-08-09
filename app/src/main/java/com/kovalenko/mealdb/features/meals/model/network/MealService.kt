package com.kovalenko.mealdb.features.meals.model.network

import com.kovalenko.mealdb.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {
    @GET("search.php")
    fun getMeals(
        @Query("s") name: String
    ): Flow<ApiResponse<NetworkMeals>>
}
