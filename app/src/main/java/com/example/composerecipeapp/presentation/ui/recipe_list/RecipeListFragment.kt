package com.example.composerecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.BaseApplication
import com.example.composerecipeapp.presentation.components.RecipeList
import com.example.composerecipeapp.presentation.components.SearchAppBar
import com.example.composerecipeapp.presentation.theme.BackgroundDark
import com.example.composerecipeapp.presentation.theme.BackgroundLight
import com.example.composerecipeapp.presentation.theme.ComposeRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    @Inject
    lateinit var baseApplication: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)

        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val loading = viewModel.isLoading.value
                val page = viewModel.page.value

                ComposeRecipeAppTheme(
                    darkTheme = baseApplication.isDarkTheme.value
                ) {
                    Column(
                        modifier = Modifier
                            .background(if (baseApplication.isDarkTheme.value) BackgroundDark else BackgroundLight)
                    ) {
                        SearchAppBar(
                            query = query,
                            categoryScrollPosition = viewModel.categoryScrollPosition,
                            selectedCategory = selectedCategory,
                            onQueryChanged = viewModel::onQueryChanged,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                            newSearch = { viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent) },
                            clearFocus = { clearFocus() },
                            onToggleTheme = { baseApplication.toggleDarkTheme() }
                        )

                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            page = page,
                            onChangeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            nextPage = { viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent) }
                        )
                    }
                }
            }
        }
    }
}