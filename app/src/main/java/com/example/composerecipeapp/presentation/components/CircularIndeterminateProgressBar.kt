package com.example.composerecipeapp.presentation.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

                CircularProgressIndicator(
                    modifier = Modifier.layoutId("progressBar"),
                    color = MaterialTheme.colors.primary
                )

                Text(
                    modifier = Modifier.layoutId("text"),
                    text = "Loading...",
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
        val progressBar = createRefFor("progressBar")
        val text = createRefFor("text")

        constrain(progressBar) {
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(text) {
            top.linkTo(progressBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}