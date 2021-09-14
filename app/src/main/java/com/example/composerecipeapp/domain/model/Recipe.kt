package com.example.composerecipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    var id: Int? = null,
    var title: String? = null,
    var publisher: String? = null,
    var featuredImage: String? = null,
    var rating: Int? = null,
    var sourceUrl: String? = null,
    var description: String? = null,
    var cooking_instructions: String? = null,
    var ingredients: List<String>? = listOf(),
    var dateAdded: String? = null,
    var dateUpdated: String? = null,
): Parcelable