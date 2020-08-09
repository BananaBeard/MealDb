package com.kovalenko.mealdb.features.meals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kovalenko.mealdb.features.meals.model.domain.Meal
import com.kovalenko.mealdb.features.meals.model.repository.MealRepository
import com.kovalenko.mealdb.util.Event
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MealDetailViewModel(private val mealRepository: MealRepository) : ViewModel() {
    private val _meal = MutableLiveData<Meal>()
    val meal: LiveData<Meal>
        get() = _meal

    private val _link = MutableLiveData<Event<String?>>()
    val link: LiveData<Event<String?>>
        get() = _link

    fun onLink(link: String?) {
        _link.value = Event(link)
    }

    fun loadMeal(id: String) {
        viewModelScope.launch {
            _meal.value = mealRepository.loadMeal(id).first()
        }
    }
}
