package com.example.composerecipeapp.presentation.ui.recipe_list

enum class FoodCategory(val value: String){
    CHICKEN(value = "Chicken"),
    BEEF(value = "Beef"),
    PORK(value = "Pork"),
    FISH(value = "Fish"),
    SOUP(value = "Soup"),
    DESSERT(value = "Dessert"),
    VEGETARIAN(value = "Vegetarian"),
    MILK(value = "Milk"),
    PIZZA(value = "Pizza"),
    DONUT(value = "Donut")
}

fun getAllFoodCategories(): List<FoodCategory> {
    return listOf(
        FoodCategory.CHICKEN,
        FoodCategory.BEEF,
        FoodCategory.PORK,
        FoodCategory.FISH,
        FoodCategory.SOUP,
        FoodCategory.DESSERT,
        FoodCategory.VEGETARIAN,
        FoodCategory.MILK,
        FoodCategory.PIZZA,
        FoodCategory.DONUT
    )
}

fun getFoodCategory(value: String): FoodCategory? {
    val map = FoodCategory.values().associateBy(FoodCategory::value)
    return map[value]
}