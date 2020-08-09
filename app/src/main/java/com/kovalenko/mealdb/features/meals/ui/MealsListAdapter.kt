package com.kovalenko.mealdb.features.meals.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kovalenko.mealdb.databinding.ItemMealBinding
import com.kovalenko.mealdb.features.meals.model.domain.Meal

class MealsListAdapter(private val callback: MealClick) :
    ListAdapter<Meal, MealsListAdapter.MealViewHolder>(MealDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MealViewHolder(private val viewDataBinding: ItemMealBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(item: Meal) {
            ViewCompat.setTransitionName(viewDataBinding.cardImage, "image_${item.id}")
            ViewCompat.setTransitionName(viewDataBinding.textMealName, "name_${item.id}")
            ViewCompat.setTransitionName(viewDataBinding.textCategory, "category_${item.id}")
            ViewCompat.setTransitionName(viewDataBinding.textCategoryName, "category_name_${item.id}")
            viewDataBinding.apply {
                meal = item
                executePendingBindings()
                root.setOnClickListener { callback.onClick(this, item.id) }
            }
        }
    }
}

class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Meal, newItem: Meal) = oldItem == newItem
}
