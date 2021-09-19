package com.example.composerecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.presentation.components.*
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalComposeUiApi
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

                ComposeRecipeAppTheme {
                    Column {

                        AnimatedHeartButton(
                            viewModel.isLiked.value,
                            viewModel::onClickLike
                        )

                        SearchAppBar(
                            query = query,
                            categoryScrollPosition = viewModel.categoryScrollPosition,
                            selectedCategory = selectedCategory,
                            onQueryChanged = viewModel::onQueryChanged,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                            newSearch = viewModel::newSearch,
                            clearFocus = { clearFocus() }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            LazyColumn {
                                itemsIndexed(
                                    items = recipes
                                ) { i: Int, recipe: Recipe ->
                                    RecipeCard(recipe = recipe, onClick = {})
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