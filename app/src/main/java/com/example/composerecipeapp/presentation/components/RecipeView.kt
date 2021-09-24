package com.example.composerecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composerecipeapp.R
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.utils.loadPicture

@Composable
fun RecipeView(
    recipe: Recipe?,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .verticalScroll(ScrollState(0))
            .fillMaxWidth()
    ) {
        Card(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .fillMaxWidth(),
            elevation = 8.dp
        ) {
            Box {
                val image = loadPicture(
                    url = recipe?.featuredImage ?: "",
                    defaultImage = R.drawable.empty_plate
                ).value

                Image(
                    bitmap = image?.asImageBitmap() ?: ImageBitmap(1, 1),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(250.dp),
                    contentScale = ContentScale.Crop
                )

                Surface(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colors.background.copy(alpha = 0.9f)
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                navController.navigateUp()
                            },
                    ) {
                        Text(
                            text = "Back",
                            style = MaterialTheme.typography.body2,
                            color = Color.White,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        )
                    }
                }

            }
        }

        Column {
            Card(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .fillMaxWidth(),
                elevation = 8.dp
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = recipe?.title ?: "",
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(0.4f)
                                .wrapContentWidth(Alignment.Start),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.Start),
                                text = "Author: ${recipe?.publisher}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.onSurface
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.Start),
                                text = "Rating: ${recipe?.rating}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.onSurface
                            )
                        }

                        Column(
                            modifier = Modifier.padding(8.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.End),
                                text = "Created: ${recipe?.dateAdded}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.onSurface
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.End),
                                text = "Updated: ${recipe?.dateUpdated}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            }

            Card(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .fillMaxWidth(),
                elevation = 8.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "Recipe:",
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.onSurface
                    )

                    recipe?.ingredients?.forEach { ingredient ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.Start)
                                .padding(8.dp),
                            text = ingredient,
                            fontSize = 16.sp,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }
}