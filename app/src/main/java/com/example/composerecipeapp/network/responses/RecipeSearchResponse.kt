package com.example.composerecipeapp.network.responses


import com.example.composerecipeapp.network.model.RecipeNetworkEntity
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("next")
    var next: String? = null,
    @SerializedName("previous")
    var previous: String? = null,
    @SerializedName("results")
    var recipeNetworkEntityList: List<RecipeNetworkEntity>? = listOf()
)