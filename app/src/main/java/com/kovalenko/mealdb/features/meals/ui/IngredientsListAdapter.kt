package com.kovalenko.mealdb.features.meals.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kovalenko.mealdb.databinding.ItemIngredientBinding
import com.kovalenko.mealdb.features.meals.model.domain.Ingredient

class IngredientsListAdapter : RecyclerView.Adapter<IngredientsListAdapter.IngredientViewHolder>() {
    var list = emptyList<Ingredient>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.ingredient = list[position]
        }
    }

    inner class IngredientViewHolder(val viewDataBinding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)
}
