package com.example.composerecipeapp.presentation.components.placeholders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerItemRecipeList(
    brush: Brush
) {
    // Column composable comtaining spacer shaped like a rectangle,
    // set the [background]'s [brush] with the brush receiving from [ShimmerAnimation]
    // Composable which is the Animation you are gonna create.

    Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
        Card (
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 8.dp
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp)
                    .background(brush = brush)
            )
        }

        Card (
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            elevation = 8.dp
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(brush = brush)
            )
        }
    }
}