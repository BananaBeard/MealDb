package com.kovalenko.mealdb.features.meals.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kovalenko.mealdb.R
import com.kovalenko.mealdb.databinding.FragmentMealsBinding
import com.kovalenko.mealdb.databinding.ItemMealBinding
import com.kovalenko.mealdb.features.meals.model.domain.Meal
import com.kovalenko.mealdb.features.meals.viewmodel.MealsViewModel
import com.kovalenko.mealdb.persistence.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealsFragment: Fragment() {
    private lateinit var viewDataBinding: FragmentMealsBinding
    private val mealsViewModel by viewModel<MealsViewModel>()
    private var mealsAdapter: MealsListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentMealsBinding.inflate(inflater, container, false).apply {
            viewmodel = mealsViewModel
            lifecycleOwner = this@MealsFragment.viewLifecycleOwner
        }
        postponeEnterTransition()
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupSubscriptions()
    }

    private fun setupSubscriptions() {
        mealsViewModel.meals.observe(this.viewLifecycleOwner, Observer(::onMealsList))
    }

    private fun onMealsList(listResource: Resource<List<Meal>>?) {
        mealsAdapter?.submitList(listResource?.data ?: emptyList())
    }

    private fun setupRecycler() {
        mealsAdapter = MealsListAdapter(MealClick(::onMealClick))
        viewDataBinding.recyclerMeals.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
            addItemDecoration(MarginItemDecorator(resources.getDimension(R.dimen.recycler_margin).toInt()))
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun onMealClick(binding: ItemMealBinding, id: String) {
        val directions = MealsFragmentDirections.mealDetail(id)
        val extras = FragmentNavigatorExtras(
            binding.cardImage to "image_$id",
            binding.textMealName to "name_$id",
            binding.textCategory to "category_$id",
            binding.textCategoryName to "category_name_$id"
        )
        findNavController().navigate(directions, extras)
    }
}

class MarginItemDecorator(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            bottom = spaceHeight
            left = spaceHeight
            right = spaceHeight
        }
    }
}

class MealClick(val block: (ItemMealBinding, String) -> Unit) {
    fun onClick(mealBinding: ItemMealBinding, id: String) = block(mealBinding, id)
}
