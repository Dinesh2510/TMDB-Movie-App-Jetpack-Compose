package com.app.movieapp.utlis

import android.app.Activity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SignalWifiStatusbarConnectedNoInternet4
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.app.movieapp.R
import com.app.movieapp.models.Movies
import kotlinx.coroutines.delay
import kotlin.random.Random

@ExperimentalMaterial3Api
@ExperimentalLayoutApi
@Composable
fun SimpleLightTopAppBar(
    title: String,
    onClickSearch: () -> Unit,
    onClickWatchList: () -> Unit,
) {

    val act = LocalContext.current as Activity
    TopAppBar(
        title = {
            Text(
                title, style = MaterialTheme.typography.titleMedium, fontFamily = netflixFamily,
                fontWeight = FontWeight.Medium,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                act.finish()
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { onClickSearch() }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(onClick = { onClickWatchList()}) {
                Icon(imageVector = Icons.Filled.Bookmarks, contentDescription = "WatchList")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )

    )

}

@ExperimentalMaterial3Api
@ExperimentalLayoutApi
@Composable
fun CenteredTopBar(
    title: String,
    onClickSearch: () -> Unit,
    onClickBack: () -> Unit
) {

    val act = LocalContext.current as Activity
    CenterAlignedTopAppBar(
        title = {
            Text(
                title, style = MaterialTheme.typography.titleMedium, fontFamily = netflixFamily,
                fontWeight = FontWeight.Medium,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onClickBack()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { onClickSearch() }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )

    )

}


@Composable
fun generateRandomGradient(): List<Color> {
    val random = Random
    val color1 = Color(random.nextFloat(), random.nextFloat(), random.nextFloat())
    val color2 = Color(random.nextFloat(), random.nextFloat(), random.nextFloat())
    return listOf(color1, color2)
}

val netflixFamily = FontFamily(
    Font(R.font.netflixsans_bold, FontWeight.Bold),
    Font(R.font.netflixsans_regular, FontWeight.Normal),
    Font(R.font.netflixsans_medium, FontWeight.Medium)

)


@Composable
fun AutoSlidingCarousel(
    images: List<Movies>,
    modifier: Modifier = Modifier,
    slideDuration: Long = 3000L,
) {
    var currentIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(slideDuration)
            currentIndex = (currentIndex + 1) % images.size
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier) {
            val imagePainter =
                rememberAsyncImagePainter(model = Constants.BASE_BACKDROP_IMAGE_URL + images[currentIndex].backdropPath)
            Image(
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))/*.pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        if (dragAmount > 0) {
                            currentIndex = (currentIndex - 1 + images.size) % images.size
                        } else {
                            currentIndex = (currentIndex + 1) % images.size
                        }
                    }
                }*/
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 300f // Adjust as needed
                        )
                    )
            )

            // Title
            Text(
                text = images[currentIndex].title,
                color = Color.White,
                fontFamily = netflixFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )


        }
        // Indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
        ) {
            images.forEachIndexed { index, _ ->
                val size by animateDpAsState(targetValue = if (index == currentIndex) 12.dp else 8.dp)
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(size)
                        .clip(CircleShape)
                        .background(if (index == currentIndex) Color.White else Color.Gray)
                )
            }
        }
    }

}


@Composable
fun CenteredCircularProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp) // Set the size of the CircularProgressIndicator
                .align(Alignment.Center) // Align it to the center of the Box
        )
    }
}

@Composable
fun ShowError(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Default.SignalWifiStatusbarConnectedNoInternet4,
                contentDescription = "contentDescription",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
            Text(message)
        }

    }


}

@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 3
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult.value = it },
            style = MaterialTheme.typography.labelMedium
        )
        if (!isExpanded && textLayoutResult.value?.hasVisualOverflow == true) {
            Text(
                text = "Read More",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { isExpanded = true }
                    .padding(top = 8.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}



