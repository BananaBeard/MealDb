package com.kovalenko.mealdb.features.meals.viewmodel

import androidx.lifecycle.*
import com.kovalenko.mealdb.features.meals.model.domain.Meal
import com.kovalenko.mealdb.features.meals.model.repository.MealRepository
import com.kovalenko.mealdb.persistence.Resource

class MealsViewModel(private val mealRepository: MealRepository) : ViewModel() {
    val nameInput = MutableLiveData<String>("")

    private val _meals = Transformations.switchMap(nameInput) { name ->
        mealRepository.loadMeals(name).asLiveData(viewModelScope.coroutineContext)
    }
    val meals: LiveData<Resource<List<Meal>>>
        get() = _meals
}
