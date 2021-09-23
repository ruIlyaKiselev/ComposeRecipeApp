package com.example.composerecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.network.ApiContract
import com.example.composerecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLiked: MutableState<Boolean> = mutableStateOf(false)
    val page = mutableStateOf(1)

    var recipeListScrollPosition = 0
    var categoryScrollPosition: Int = 0

    init {
        query.value = ""
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(handler + Dispatchers.IO) {
            when(event) {
                is RecipeListEvent.NewSearchEvent -> {
                    newSearch()
                }
                is RecipeListEvent.NextPageEvent -> {
                    nextPage()
                }
            }
        }
    }

    //    use case #1
    private suspend fun newSearch() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        isLoading.value = true
        resetSearchState()
        recipes.value = repository.search(token, 1, query.value)
        isLoading.value = false
    }

    //    use case #2
    private suspend fun nextPage() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        if (recipeListScrollPosition + 1 >= page.value * ApiContract.PAGE_SIZE) {
            isLoading.value = true
            incrementPage()
            Log.d("MyLog", "Next Page triggered: ${page.value}")

            delay(1000)

            if (page.value > 1) {
                val result = repository.search(
                    token = ApiContract.TOKEN,
                    page = page.value,
                    query = query.value
                )

                Log.d("MyLog", "Next Page: $result")
                appendRecipes(result)
            }

            isLoading.value = false
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    private fun resetSearchState() {
        recipes.value = listOf()

        page.value = 1
        onChangeRecipeScrollPosition(0)

        if (query.value != selectedCategory.value?.value) {
            clearSelectedCategory()
        }
    }

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }



    public fun onClickLike() {
        isLiked.value = !isLiked.value
    }
}