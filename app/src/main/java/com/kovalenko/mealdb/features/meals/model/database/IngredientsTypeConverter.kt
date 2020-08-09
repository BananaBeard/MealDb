package com.kovalenko.mealdb.features.meals.model.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kovalenko.mealdb.features.meals.model.domain.Ingredient

class IngredientsTypeConverter {
    @TypeConverter
    fun fromIngredientList(list: List<Ingredient>?): String? {
        return list?.let {
            val gson = Gson()
            val type = object : TypeToken<List<Ingredient>>() {}.type
            gson.toJson(list, type).toString()
        }
    }

    @TypeConverter
    fun toIngredientList(json: String?): List<Ingredient> {
        return json?.let {
            val gson = Gson()
            val type = object : TypeToken<List<Ingredient>>() {}.type
            gson.fromJson<List<Ingredient>>(it, type)
        } ?: emptyList()
    }
}
