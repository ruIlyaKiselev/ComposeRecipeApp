package com.example.composerecipeapp.presentation.components

import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun AnimatedHeartButton(
    isLiked: Boolean,
    onClick: () -> Unit
) {
    val scale = animateFloatAsState(
        targetValue = if (isLiked) 1.0001f else 1f,
        animationSpec = keyframes {
            durationMillis = 350
            1f at 70 with LinearOutSlowInEasing
            1.25f at 140 with FastOutLinearInEasing
            0.8f at 210 with FastOutSlowInEasing
            1.1f at 280 with FastOutLinearInEasing
        }
    )

    val color by animateColorAsState(
        targetValue = if (isLiked)  Red  else Gray,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
    )

    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .scale(scale.value)
                .width(25.dp)
                .height(25.dp)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            onClick()
                        }
                    }
                    true
                },
            imageVector = Icons.Default.Favorite,
            contentDescription = "",
            colorFilter = ColorFilter.tint(color)
        )
    }
}