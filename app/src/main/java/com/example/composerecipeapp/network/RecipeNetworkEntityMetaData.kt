package com.example.composerecipeapp.network


import com.google.gson.annotations.SerializedName

data class RecipeNetworkEntityMetaData(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("next")
    var next: String? = null,
    @SerializedName("previous")
    var previous: String? = null,
    @SerializedName("results")
    var recipeNetworkEntityMetaData: List<RecipeNetworkEntity>? = listOf()
)