package com.example.composerecipeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.network.RecipeService
import com.example.composerecipeapp.network.model.RecipeDto
import com.example.composerecipeapp.network.model.RecipeDtoMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapper = RecipeDtoMapper()
        val recipe = Recipe()
        val dto: RecipeDto = mapper.mapFromDomainModel(recipe)
        var restoredRecipe: Recipe = mapper.mapToDomainModel(dto)

        val service = Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val result = service.search(
                "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                1,
                "carrot"
            )

            Log.d("MyLog", result.recipeDtoList.toString())
        }
    }
}