package com.example.composerecipeapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    var categoryScrollPosition: Int = 0

    init {
        query.value = ""
        newSearch()
    }

    fun newSearch() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(Dispatchers.IO + handler) {
            isLoading.value = true
            resetSearchState()
            recipes.value = repository.search(token, 1, query.value)
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

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    private fun resetSearchState() {
        recipes.value = listOf()

        if (query.value != selectedCategory.value?.value) {
            clearSelectedCategory()
        }
    }

    public fun onClickLike() {
        isLiked.value = !isLiked.value
    }
}