package com.example.composerecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.composerecipeapp.presentation.components.RecipeCard
import com.example.composerecipeapp.presentation.components.SearchAppBar
import com.example.composerecipeapp.presentation.components.foodCategoryChip
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

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

                ComposeRecipeAppTheme {
                    Column {

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