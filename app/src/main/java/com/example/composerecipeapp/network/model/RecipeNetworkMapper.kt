package com.example.composerecipeapp.network.model

import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.domain.util.EntityMapper

class RecipeNetworkMapper: EntityMapper<RecipeNetworkEntity, Recipe> {
    override fun mapFromEntity(entity: RecipeNetworkEntity): Recipe {
        return Recipe(
            id = entity.pk,
            title = entity.title,
            publisher = entity.publisher,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            sourceUrl = entity.sourceUrl,
            description = entity.description,
            cooking_instructions = entity.cookingInstructions,
            ingredients = entity.ingredients,
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated
        )
    }

    override fun mapToEntity(domainModel: Recipe): RecipeNetworkEntity {
        return RecipeNetworkEntity(
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

    fun fromEntityList(initial: List<RecipeNetworkEntity>): List<Recipe> {
        return initial.map {
            mapFromEntity(it)
        }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeNetworkEntity> {
        return initial.map {
            mapToEntity(it)
        }
    }
}