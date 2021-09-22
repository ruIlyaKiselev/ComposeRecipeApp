package com.example.composerecipeapp

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    val isDarkTheme = mutableStateOf(false)

    fun toggleDarkTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}