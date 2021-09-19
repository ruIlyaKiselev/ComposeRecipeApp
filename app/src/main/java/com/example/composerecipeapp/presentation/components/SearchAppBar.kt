package com.example.composerecipeapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.composerecipeapp.presentation.ui.recipe_list.FoodCategory
import com.example.composerecipeapp.presentation.ui.recipe_list.getAllFoodCategories
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchAppBar(
    query: String,
    categoryScrollPosition: Int,
    selectedCategory: FoodCategory?,
    onQueryChanged: (String) -> Unit,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    newSearch: () -> Unit,
    clearFocus: () -> Unit
) {
    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        color = MaterialTheme.colors.surface
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = query,
                    onValueChange = { newValue ->
                        onQueryChanged(newValue)
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
                            newSearch()
                            clearFocus()
                        }
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        background = Color.Transparent
                    )
                )
            }

            val scrollState = rememberScrollState()
            val coroutineScope = rememberCoroutineScope()

            Row(
                modifier = Modifier
                    .horizontalScroll(
                        enabled = true,
                        state = scrollState,
                    )
                    .fillMaxWidth()
                    .padding(start = 8.dp),
            ) {
                coroutineScope.launch {
                    scrollState.scrollTo(categoryScrollPosition)
                }
                getAllFoodCategories().forEach { foodCategory ->
                    foodCategoryChip(
                        category = foodCategory.value,
                        isSelected = selectedCategory == foodCategory,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(foodCategory.value)
                            onChangeCategoryScrollPosition(scrollState.value)
                        },
                        onExecuteSearch = {
                            newSearch()
                        }
                    )
                }
            }
        }
    }
}