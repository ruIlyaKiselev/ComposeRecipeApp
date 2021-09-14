package com.example.composerecipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeMetaData(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val recipes: List<Recipe>?
): Parcelable