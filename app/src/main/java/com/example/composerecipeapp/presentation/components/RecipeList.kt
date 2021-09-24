package com.example.composerecipeapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.composerecipeapp.R
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.network.ApiContract
import com.example.composerecipeapp.presentation.components.placeholders.ShimmerAnimation
import com.example.composerecipeapp.presentation.ui.RecipeNamingContract

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    page: Int,
    onChangeScrollPosition: (Int) -> Unit,
    nextPage: () -> Unit,
    navController: NavController
) {

//    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (loading && recipes.isEmpty()) {
            LazyColumn {
                items(5) {
                    ShimmerAnimation()
                }
            }
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index: Int, recipe: Recipe ->
//                        viewModel.onChangeRecipeScrollPosition(index)
                    onChangeScrollPosition(index)
                    if (index + 1 >= page * ApiContract.PAGE_SIZE - 5 && !loading) {
//                            viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                        nextPage()
                    }

                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if (recipe.id != null) {
                                val bundle = Bundle()
                                bundle.putInt(RecipeNamingContract.RECIPE_ID, recipe.id ?: 0)
                                navController.navigate(R.id.viewRecipe, bundle)
                            } else {
//                                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }

        CircularIndeterminateProgressBar(isDisplayed = loading)
    }
}


