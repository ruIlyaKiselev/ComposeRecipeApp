package com.example.composerecipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int?,
    val title: String?,
    val publisher: String?,
    val featuredImage: String?,
    val rating: Int?,
    val sourceUrl: String?,
    val description: String?,
    val cooking_instructions: String?,
    val ingredients: List<String>?,
    val dateAdded: String?,
    val dateUpdated: String?,
): Parcelable