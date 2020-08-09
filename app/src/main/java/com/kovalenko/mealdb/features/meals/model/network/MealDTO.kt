package com.kovalenko.mealdb.features.meals.model.network

import com.kovalenko.mealdb.features.meals.model.database.DatabaseMeal
import com.kovalenko.mealdb.features.meals.model.domain.Ingredient

data class NetworkMeals(
    val meals: List<NetworkMeal>?
)

data class NetworkMeal(
    val idMeal: String,
    val strMeal: String?,
    val strDrinkAlternate: String?,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?,
    val strTags: String?,
    val strYoutube: String?,
    val strSource: String?,
    val dateModified: String?,
    val ingredients: List<Ingredient>
)

fun List<NetworkMeal>.asDatabaseModel() = map { it.asDatabaseModel() }

fun NetworkMeal.asDatabaseModel(): DatabaseMeal {
    return DatabaseMeal(
        id = this.idMeal,
        name = this.strMeal?.trim(),
        strDrinkAlternate = this.strDrinkAlternate,
        category = this.strCategory,
        area = this.strArea,
        instructions = this.strInstructions,
        thumb = this.strMealThumb,
        tags = this.strTags,
        youtube = this.strYoutube,
        source = this.strSource,
        dateModified = this.dateModified,
        ingredients = this.ingredients
    )
}
