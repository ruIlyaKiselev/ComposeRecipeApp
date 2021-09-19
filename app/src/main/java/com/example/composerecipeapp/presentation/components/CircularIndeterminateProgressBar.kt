package com.example.composerecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val constraints = if(minWidth < 600.dp) {
                myDecoupledConstraints(0.3f)
            } else {
                myDecoupledConstraints(0.2f)
            }

            ConstraintLayout(
                constraintSet = constraints,
                modifier = Modifier.fillMaxSize()
            ) {

                Pulsating(
                    content = {
                        Image(
                            modifier = Modifier
                                .width(200.dp),
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Red)
                        )
                    },
                    layoutId = "pulsating",
                    pulseFraction = 1.5f
                )
                
                Text(
                    modifier = Modifier
                        .layoutId("text")
                        .padding(top = 8.dp),
                    text = " Loading...",
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

private fun myDecoupledConstraints(verticalBias: Float): ConstraintSet {
    return ConstraintSet {
        val guideline = createGuidelineFromTop(verticalBias)
        val pulsating = createRefFor("pulsating")
        val text = createRefFor("text")



        constrain(pulsating) {
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text) {
            top.linkTo(pulsating.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}