package com.app.movieapp.screens.Componets

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.app.movieapp.R
import com.app.movieapp.graph.MovieAppScreen
import com.app.movieapp.models.Movies
import com.app.movieapp.utlis.Constants
import com.app.movieapp.utlis.Constants.Companion.BASE_POSTER_IMAGE_URL
import com.app.movieapp.utlis.netflixFamily

class MovieUIComponents : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun HomeThumbWithTitle(
    homeMediaUI: Movies,
    onClickMovies: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.normal_padding_half))
            .width(dimensionResource(id = R.dimen.home_grid_card_width))
            .height(dimensionResource(id = R.dimen.home_grid_card_height)), onClick = {
            onClickMovies()
        }
    ) {
        Column {
            AsyncImage(
                model = Constants.BASE_POSTER_IMAGE_URL + homeMediaUI.posterPath,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(dimensionResource(id = R.dimen.home_grid_poster_height))
            )
            Text(
                text = homeMediaUI.title,
                fontFamily = netflixFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.small_padding))
                    .fillMaxSize()
                    .wrapContentHeight(align = CenterVertically),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

@Composable
fun HomeThumbRectWithTitle(
    imageUrl: String,
    title: String,
    OpenDetailsPage: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.normal_padding_half))
            .width(220.dp)
            .height(130.dp), onClick = {
            OpenDetailsPage()
        }
    ) {
        Box( // Use Box for layering elements
            modifier = Modifier.fillMaxSize()
        ) {
            // Background image
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            // Overlay with gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    )
            )

            // Title on bottom
            Text(
                text = title,
                fontFamily = netflixFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.small_padding))
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = Color.White // Set white color for text on overlay
            )
            /* Text(
                    text = "$year • $genre • $duration",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start
                    )
                )*/
        }
    }
}


@Composable
fun SearchMovieCard(
    imageUrl: String,
    title: String?,
    overview: String?,
    OpenDetailsPage: () -> Unit,
) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = {
            OpenDetailsPage()
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                3.dp
            )
        ),
    ) {
        Row(verticalAlignment = CenterVertically) {
            Card(Modifier.size(128.dp)) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                            .apply(block = fun ImageRequest.Builder.() {
                                size(Size.ORIGINAL)
                                scale(Scale.FILL)
                                crossfade(true)
                            }).build()
                    ),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }
            Column(
                Modifier
                    .padding(vertical = 14.dp, horizontal = 14.dp)
                    .weight(1f)
            ) {
                if (title != null) {
                    Text(
                        text = title, maxLines = 1,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500)
                    )
                }
                if (overview != null) {
                    Text(
                        text = overview, maxLines = 2,
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

        }
    }
}

@Composable
fun HomeSmallThumb(imageUrl: String, OpenDetailsPage: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clickable(onClick = {
                Log.e("TAG", "HomeSmallThumb: ")
                OpenDetailsPage()
            }),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.primary.copy(0.5f)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (imageUrl != null) {

                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                            .apply(block = fun ImageRequest.Builder.() {
                                size(Size.ORIGINAL)
                                scale(Scale.FILL)
                                crossfade(true)
                            }).build()
                    ),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            } else {

                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.placeholder_error),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }

        }
    }
    Spacer(modifier = Modifier.width(16.dp))
}

@Composable
fun HomeGenre(name: String, onclick: () -> Unit) {
    val defaultDominantColor = MaterialTheme.colorScheme.primaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominantColor
                    )
                )
            )
            .clickable {
                onclick()
            }
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 4.dp
                ),
            text = name,
            fontFamily = netflixFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            color = Color.White,
            overflow = TextOverflow.Ellipsis
        )
    }

    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
fun HomeHeader(title: String, onClick: () -> Unit, showMore: Boolean) {
    var iconPos by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = netflixFamily,
                fontWeight = FontWeight.Normal,
            )
            if (title == "Popular Movies")
                IconButton(onClick = { iconPos = !iconPos }) {
                    Icon(
                        imageVector = if (iconPos) Icons.Default.ArrowDropUp else Icons.Default.Info,
                        contentDescription = "Button"
                    )
                }
        }
        if (showMore) {
            TextButton(onClick = { onClick() }) {
                Text(
                    text = stringResource(R.string.movies_more),
                    color = Color.White,
                    fontFamily = netflixFamily,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.titleSmall
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
    }

    AnimatedVisibility(visible = iconPos) {
        Box(
            Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .border(1.dp, Color.White)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "This listing has Paging 3",
                style = MaterialTheme.typography.titleMedium,
                fontFamily = netflixFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}





@Composable
fun ErrorStrip(message: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp),
                painter = painterResource(id = R.drawable.baseline_error_24),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                color = Color.White,
                text = message,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
    }
}

@Composable
fun MovieItemSeeAll(
    media: Movies,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    val imageUrl = "${BASE_POSTER_IMAGE_URL}${media.posterPath}"

    val title = media.title

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val imageState = imagePainter.state

    val defaultDominantColor = MaterialTheme.colorScheme.primaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        modifier = modifier.padding(
            bottom = 16.dp,
            start = 8.dp,
            end = 8.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            dominantColor
                        )
                    )
                )
                .clickable {
                    navController.navigate(MovieAppScreen.MOVIE_HOME_DETAILS.route + "/${media.id}")
                }
        ) {

            Box(
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxSize()
                    .padding(6.dp)
            ) {

                if (imageState is AsyncImagePainter.State.Success) {

                    val imageBitmap = imageState.result.drawable.toBitmap()



                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.background),
                    )

                }

                if (imageState is AsyncImagePainter.State.Error) {
                    dominantColor = MaterialTheme.colorScheme.primary
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(32.dp)
                            .alpha(0.8f),
                        painter = painterResource(id = R.drawable.ic_broken_image),
                        contentDescription = title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }


                if (imageState is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.Center)
                            .scale(0.5f)
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    ),
                text = title,
                fontFamily = netflixFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 4.dp,
                        start = 11.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    ),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC700),
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp)
                    )

                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = 4.dp
                            ),
                        text = media.voteAverage.toString().take(3),
                        fontFamily = netflixFamily,
                        fontSize = 14.sp,
                        maxLines = 1,
                        color = Color.LightGray
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
        SwipeToDismissBoxValue.EndToStart -> Color(0xFFFF1744)
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        /*Icon(
            // make sure add baseline_archive_24 resource to drawable folder
            painter = painterResource(R.drawable.ic_home),
            contentDescription = "Archive"
        )*/
        Spacer(modifier = Modifier)

        Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
    }
}