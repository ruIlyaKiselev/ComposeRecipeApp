package com.example.composerecipeapp.network.model

import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.domain.util.DomainMapper

class RecipeDtoMapper: DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            publisher = model.publisher,
            featuredImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceUrl,
            description = model.description,
            cooking_instructions = model.cookingInstructions,
            ingredients = model.ingredients,
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            pk = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstructions = domainModel.cooking_instructions,
            ingredients = domainModel.ingredients,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated
        )
    }

    fun fromEntityList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map {
            mapToDomainModel(it)
        }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map {
            mapFromDomainModel(it)
        }
    }
}