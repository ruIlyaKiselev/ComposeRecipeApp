package com.example.composerecipeapp.network


import com.google.gson.annotations.SerializedName

data class RecipeNetworkEntityMetaData(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val recipeNetworkEntityMetaData: List<RecipeNetworkEntity>?
)