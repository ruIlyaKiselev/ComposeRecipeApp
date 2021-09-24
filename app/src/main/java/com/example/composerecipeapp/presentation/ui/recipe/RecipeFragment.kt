package com.example.composerecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.BaseApplication
import com.example.composerecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.composerecipeapp.presentation.theme.BackgroundDark
import com.example.composerecipeapp.presentation.theme.BackgroundLight
import com.example.composerecipeapp.presentation.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.presentation.ui.RecipeNamingContract
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {

    @Inject
    lateinit var baseApplication: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()
    private var recipeId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeId = arguments?.getInt(RecipeNamingContract.RECIPE_ID)
        viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId ?: 0))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                ComposeRecipeAppTheme(
                    darkTheme = baseApplication.isDarkTheme.value
                ) {
                    Column(
                        modifier = Modifier
                            .background(if (baseApplication.isDarkTheme.value) BackgroundDark else BackgroundLight)
                    ) {
                        Text(
                            text = "Recipe Fragment (id = $recipeId)",
                            color = Color.Red,
                            fontSize = 42.sp
                        )

                        CircularIndeterminateProgressBar(isDisplayed = loading)
                    }
                }
            }
        }
    }
}