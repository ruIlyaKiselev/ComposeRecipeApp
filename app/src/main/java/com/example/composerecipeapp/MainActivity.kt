package com.example.composerecipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.network.RecipeNetworkEntity
import com.example.composerecipeapp.network.RecipeNetworkMapper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapper = RecipeNetworkMapper()
        val recipe = Recipe()
        val networkEntity: RecipeNetworkEntity = mapper.mapToEntity(recipe)
        var restoredRecipe: Recipe = mapper.mapFromEntity(networkEntity)
    }
}