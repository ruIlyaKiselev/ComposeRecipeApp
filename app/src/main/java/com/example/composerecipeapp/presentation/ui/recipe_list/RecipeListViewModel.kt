package com.example.composerecipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.composerecipeapp.network.ApiContract
import com.example.composerecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val randomString: String,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel() {

    init {
        Log.d("MyLog", randomString)

        GlobalScope.launch {
            Log.d("MyLog", repository.search(ApiContract.TOKEN, 1, "potato")[0].dateAdded!!)
        }
    }

    fun getRepo() = repository
    fun getRandomString() = randomString
    fun  getToken() = token

}