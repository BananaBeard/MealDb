package com.kovalenko.mealdb.features.meals.model.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.kovalenko.mealdb.features.meals.model.domain.Ingredient
import java.lang.reflect.Type

class MealDeserializer : JsonDeserializer<NetworkMeal> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NetworkMeal {
        val jsonObject = json!!.asJsonObject
        val ingredients = mutableListOf<Ingredient>()

        for (i in 1..20) {
            val ingredient = jsonObject.asStringOrNull("strIngredient$i")
            if (!ingredient.isNullOrEmpty()) {
                val measure = jsonObject["strMeasure$i"].asString
                ingredients.add(Ingredient(ingredient.capitalize(), measure!!))
            }
        }
        return NetworkMeal(
            idMeal = jsonObject["idMeal"].asString,
            strMeal = jsonObject.asStringOrNull("strMeal"),
            strDrinkAlternate = jsonObject.asStringOrNull("strDrinkAlternate"),
            strCategory = jsonObject.asStringOrNull("strCategory"),
            strArea = jsonObject.asStringOrNull("strArea"),
            strInstructions = jsonObject.asStringOrNull("strInstructions"),
            strMealThumb = jsonObject.asStringOrNull("strMealThumb"),
            strTags = jsonObject.asStringOrNull("strTags"),
            strYoutube = jsonObject.asStringOrNull("strYoutube"),
            strSource = jsonObject.asStringOrNull("strSource"),
            dateModified = jsonObject.asStringOrNull("dateModified"),
            ingredients = ingredients
        )
    }
}

fun JsonObject.asStringOrNull(name: String) =
    if (this[name].isJsonNull) null else this[name].asString
