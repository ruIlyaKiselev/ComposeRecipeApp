package com.example.composerecipeapp.network


import com.google.gson.annotations.SerializedName

data class RecipeNetworkEntity(
    @SerializedName("pk")
    val pk: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("source_url")
    val sourceUrl: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("cooking_instructions")
    val cookingInstructions: String?,
    @SerializedName("date_added")
    val dateAdded: String?,
    @SerializedName("date_updated")
    val dateUpdated: String?,
    @SerializedName("ingredients")
    val ingredients: List<String>?,
    @SerializedName("featured_image")
    val featuredImage: String?,
)