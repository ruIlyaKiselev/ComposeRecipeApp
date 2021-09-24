package com.example.composerecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.presentation.ui.RecipeNamingContract
import com.example.composerecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(RecipeNamingContract.STATE_KEY_RECIPE)?.let { recipeId ->
            onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(recipeEvent: RecipeEvent.GetRecipeEvent) {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(handler + Dispatchers.IO) {
            when(recipeEvent) {
                is RecipeEvent.GetRecipeEvent -> {
                    if (recipe.value == null) {
                        getRecipe(recipeEvent.id)
                    }
                }
            }
        }
    }

    private suspend fun getRecipe(recipeId: Int) {
        loading.value = true

        val recipeFromRepository = repository.get(
            token = token,
            id = recipeId
        )

        this.recipe.value = recipeFromRepository

        savedStateHandle.set(RecipeNamingContract.RECIPE_ID, recipeFromRepository.id)
        loading.value = false
        Log.d("MyLog", recipeFromRepository.toString())
    }
}