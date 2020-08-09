package com.kovalenko.mealdb.features.meals.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kovalenko.mealdb.R
import com.kovalenko.mealdb.databinding.FragmentMealDetailBinding
import com.kovalenko.mealdb.features.meals.viewmodel.MealDetailViewModel
import com.kovalenko.mealdb.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealDetailFragment : Fragment() {
    private val mealDetailViewModel by viewModel<MealDetailViewModel>()
    private val args by navArgs<MealDetailFragmentArgs>()
    private var ingredientsAdapter: IngredientsListAdapter? = null
    private lateinit var viewDataBinding: FragmentMealDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentMealDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MealDetailFragment.viewLifecycleOwner
            viewmodel = mealDetailViewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewTransition()
        setupRecycler()
        setupSubscriptions()
        mealDetailViewModel.loadMeal(args.mealId)
    }

    private fun setupViewTransition() {
        ViewCompat.setTransitionName(viewDataBinding.cardImage, "image_${args.mealId}")
        ViewCompat.setTransitionName(viewDataBinding.textName, "name_${args.mealId}")
        ViewCompat.setTransitionName(viewDataBinding.textCategory, "category_${args.mealId}")
        ViewCompat.setTransitionName(
            viewDataBinding.textCategoryName,
            "category_name_${args.mealId}"
        )
    }

    private fun setupSubscriptions() {
        mealDetailViewModel.meal.observe(this.viewLifecycleOwner, Observer {
            scheduleStartPostponedTransition(viewDataBinding.imageMeal)
            ingredientsAdapter?.list = it.ingredients ?: emptyList()
        })

        mealDetailViewModel.link.observe(this.viewLifecycleOwner, EventObserver(::openLink))
    }

    private fun setupRecycler() {
        ingredientsAdapter = IngredientsListAdapter()
        viewDataBinding.recyclerIngredients.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ingredientsAdapter
            addItemDecoration(
                MarginItemDecorator(
                    resources.getDimension(R.dimen.recycler_margin).toInt()
                )
            )
        }
    }

    private fun openLink(link: String?) {
        if (link.isNullOrEmpty()) {
            Toast.makeText(context, "Link is not available.", Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }

    private fun scheduleStartPostponedTransition(sharedElement: View) {
        sharedElement.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    sharedElement.viewTreeObserver.removeOnPreDrawListener(this)
                    startPostponedEnterTransition()
                    return true
                }
            })
    }
}
