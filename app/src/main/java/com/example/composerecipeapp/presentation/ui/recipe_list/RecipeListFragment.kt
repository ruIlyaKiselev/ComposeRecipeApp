package com.example.composerecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.BaseApplication
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.composerecipeapp.presentation.components.RecipeCard
import com.example.composerecipeapp.presentation.components.SearchAppBar
import com.example.composerecipeapp.presentation.components.placeholders.ShimmerAnimation
import com.example.composerecipeapp.presentation.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.presentation.theme.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    @Inject
    lateinit var baseApplication: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.newSearch()

        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val loading = viewModel.isLoading.value

                ComposeRecipeAppTheme (
                    darkTheme = baseApplication.isDarkTheme.value
                ) {
                    Column (
                        modifier = Modifier
                            .background(if (baseApplication.isDarkTheme.value) BackgroundDark else BackgroundLight)
                    ) {
                        SearchAppBar(
                            query = query,
                            categoryScrollPosition = viewModel.categoryScrollPosition,
                            selectedCategory = selectedCategory,
                            onQueryChanged = viewModel::onQueryChanged,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                            newSearch = viewModel::newSearch,
                            clearFocus = { clearFocus() },
                            onToggleTheme = { baseApplication.toggleDarkTheme() }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            if (loading) {
                                LazyColumn {
                                    items(5) {
                                        ShimmerAnimation()
                                    }
                                }
                            } else {
                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { i: Int, recipe: Recipe ->
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }
                            }

                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                    }
                }
            }
        }
    }
}