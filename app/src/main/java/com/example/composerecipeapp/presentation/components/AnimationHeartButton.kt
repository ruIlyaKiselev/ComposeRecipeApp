package com.example.composerecipeapp.presentation.components

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
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
    val scale = animateFloatAsState(if (isLiked) 1.2f else 1f)
    val color = if (isLiked) Red else Gray

    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .scale(scale.value)
                .width(50.dp)
                .height(50.dp)
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