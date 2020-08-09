package com.kovalenko.mealdb.features.meals.model.domain

data class Meal (
    val id: String,
    val name: String?,
    val strDrinkAlternate: String?,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val thumb: String?,
    val tags: String?,
    val youtube: String?,
    val source: String?,
    val dateModified: String?,
    val ingredients: List<Ingredient>?
)

data class Ingredient(
    val name: String,
    val measure: String
)
