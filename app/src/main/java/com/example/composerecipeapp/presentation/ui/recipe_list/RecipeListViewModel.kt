package com.example.composerecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.network.ApiContract
import com.example.composerecipeapp.repository.RecipeRepository
import dagger.assisted.Assisted
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
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val page = mutableStateOf(1)

    var recipeListScrollPosition = 0
    var categoryScrollPosition: Int = 0

    init {
        savedStateHandle.get<Int>(SavedStateContract.STATE_KEY_PAGE)?.let {
            setPage(it)
        }
        savedStateHandle.get<String>(SavedStateContract.STATE_KEY_QUERY)?.let {
            setQuery(it)
        }
        savedStateHandle.get<Int>(SavedStateContract.STATE_KEY_LIST_POSITION)?.let {
            setListScrollPosition(it)
        }
        savedStateHandle.get<Int>(SavedStateContract.STATE_KEY_SELECTED_CATEGORY_POSITION)?.let {
            setSelectedCategoryScrollPosition(it)
        }
        savedStateHandle.get<FoodCategory>(SavedStateContract.STATE_KEY_SELECTED_CATEGORY)?.let {
            setSelectedCategory(it)
        }

        if (recipeListScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
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
                is RecipeListEvent.RestoreStateEvent -> {
                    restoreState()
                }
            }
        }
    }

    private suspend fun restoreState() {
        isLoading.value = true
        val results: MutableList<Recipe> = mutableListOf()

        for (page in 1..page.value) {
            val result = repository.search(
                token = ApiContract.TOKEN,
                page = page,
                query = query.value
            )
            results.addAll(result)
            if (page == this.page.value) {
                recipes.value = results
                isLoading.value = false
            }
        }
    }

    //    use case #1
    private suspend fun newSearch() {
        isLoading.value = true
        resetSearchState()
        recipes.value = repository.search(token, 1, query.value)
        isLoading.value = false
    }

    //    use case #2
    private suspend fun nextPage() {
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
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Int) {
        setSelectedCategoryScrollPosition(position)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position)
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    private fun resetSearchState() {
        recipes.value = listOf()

        setPage(1)
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
        setPage(page.value + 1)
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(SavedStateContract.STATE_KEY_LIST_POSITION, position)
    }

    private fun setSelectedCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
        savedStateHandle.set(SavedStateContract.STATE_KEY_SELECTED_CATEGORY_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(SavedStateContract.STATE_KEY_PAGE, page)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(SavedStateContract.STATE_KEY_QUERY, query)
    }

    private fun setSelectedCategory(selectedCategory: FoodCategory) {
        this.selectedCategory.value = selectedCategory
        savedStateHandle.set(SavedStateContract.STATE_KEY_SELECTED_CATEGORY, selectedCategory)
    }
}