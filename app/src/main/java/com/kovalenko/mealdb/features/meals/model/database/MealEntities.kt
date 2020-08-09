package com.kovalenko.mealdb.features.meals.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kovalenko.mealdb.features.meals.model.domain.Ingredient
import com.kovalenko.mealdb.features.meals.model.domain.Meal

@Entity
@TypeConverters(IngredientsTypeConverter::class)
data class DatabaseMeal constructor(
    @PrimaryKey
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

fun List<DatabaseMeal>.asDomainModel() = map { it.asDomainModel() }

fun DatabaseMeal.asDomainModel(): Meal {
    return Meal(
        id = this.id,
        name = this.name,
        strDrinkAlternate = this.strDrinkAlternate,
        category = this.category,
        area = this.area,
        instructions = this.instructions,
        thumb = this.thumb,
        tags = this.tags,
        youtube = this.youtube,
        source = this.source,
        dateModified = this.dateModified,
        ingredients = this.ingredients
    )
}
