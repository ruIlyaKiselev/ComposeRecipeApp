package com.example.composerecipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe (
    val id: Int
): Parcelable