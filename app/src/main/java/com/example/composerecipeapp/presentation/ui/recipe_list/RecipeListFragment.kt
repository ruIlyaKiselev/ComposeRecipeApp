package com.example.composerecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import com.example.composerecipeapp.presentation.components.RecipeCard
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                ComposeRecipeAppTheme {
                    Column {
                        Column {
                            Surface(
                                elevation = 8.dp,
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colors.primary
                            ) {
                                Column {


                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        TextField(
                                            value = query,
                                            onValueChange = { newValue ->
                                                viewModel.onQueryChanged(newValue)
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .padding(16.dp)
                                                .background(
                                                    color = MaterialTheme.colors.surface,
                                                    shape = MaterialTheme.shapes.small
                                                ),
                                            label = {
                                                Text(text = "Search")
                                            },
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Text,
                                                imeAction = ImeAction.Search
                                            ),
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Filled.Search,
                                                    contentDescription = "Search icon"
                                                )
                                            },
                                            keyboardActions = KeyboardActions(
                                                onSearch = {
                                                    viewModel.newSearch(query)
                                                    clearFocus()
                                                }
                                            ),
                                            textStyle = TextStyle(
                                                color = MaterialTheme.colors.onSurface,
                                                background = Color.Transparent
                                            )
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .horizontalScroll(
                                                enabled = true,
                                                state = ScrollState(0)
                                            )
                                            .fillMaxWidth()
                                    ) {
                                        getAllFoodCategories().forEach { foodCategory ->
                                            Text(
                                                text = foodCategory.value,
                                                style = MaterialTheme.typography.body2,
                                                color = MaterialTheme.colors.secondary,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        LazyColumn {
                            itemsIndexed(
                                items = recipes
                            ) { i: Int, recipe: Recipe ->
                                RecipeCard(recipe = recipe, onClick = {})
                            }
                        }
                    }
                }
            }
        }
    }
}