package com.app.movieapp.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.app.movieapp.R
import com.app.movieapp.data.local.WatchListModel
import com.app.movieapp.data.remote.response.MovieDetailsDTO
import com.app.movieapp.data.remote.response.MovieResponse
import com.app.movieapp.data.viewmodel.MovieDetailsViewModel
import com.app.movieapp.data.viewmodel.WatchListViewModel
import com.app.movieapp.graph.MovieAppScreen
import com.app.movieapp.models.Cast
import com.app.movieapp.screens.Componets.HomeSmallThumb
import com.app.movieapp.utlis.CenteredCircularProgressIndicator
import com.app.movieapp.utlis.Constants
import com.app.movieapp.utlis.Constants.Companion.BASE_POSTER_IMAGE_URL
import com.app.movieapp.utlis.MovieState
import com.app.movieapp.utlis.ShowError
import com.app.movieapp.utlis.netflixFamily
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun MovieDetailsScreen(navController: NavHostController, movieId: String) {
    var primaryColor = Color(0xFF001945)
    var viewModel: MovieDetailsViewModel = hiltViewModel()
    var watchListViewModel: WatchListViewModel = hiltViewModel()
    val detailsMovieState by viewModel.detailsMovieResponses.collectAsState()
    val castMovieState by viewModel.castMovieResponses.collectAsState()
    val similarMovieState by viewModel.similarMovieResponses.collectAsState()

    // Fetch data on launch (optional):
    LaunchedEffect(Unit) {
        viewModel.fetchMoviesDetails(movieId)
        viewModel.fetchSimilarMovies(movieId)
        viewModel.fetchCasteOfMovies(movieId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
            .verticalScroll(rememberScrollState())
    ) {
        when (detailsMovieState) {
            is MovieState.Success -> {
                val moviesInfo = (detailsMovieState as MovieState.Success<MovieDetailsDTO?>).data
                DisplayMovieData(moviesInfo, navController,watchListViewModel)
            }

            is MovieState.Error -> {
                ShowError((detailsMovieState as MovieState.Error).message)
            }

            is MovieState.Loading -> {
                CenteredCircularProgressIndicator()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (castMovieState) {
            is MovieState.Success -> {
                val castList =
                    ((castMovieState as MovieState.Success<List<Cast>?>).data as? List<Cast>)
                        ?: emptyList()
                CastMediaSection(castList)
            }

            is MovieState.Error -> {
                ShowError((castMovieState as MovieState.Error).message)
            }

            is MovieState.Loading -> {
                CenteredCircularProgressIndicator()
                // You can show a separate loading indicator here if desired
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (similarMovieState) {
            is MovieState.Success -> {
                val movieList = (similarMovieState as MovieState.Success<MovieResponse?>).data
                if (movieList != null) {
                    SimilarMediaSection(movieList,navController)
                }
            }

            is MovieState.Error -> {
                ShowError((similarMovieState as MovieState.Error).message)
            }

            is MovieState.Loading -> {
                CenteredCircularProgressIndicator()
                // You can show a separate loading indicator here if desired
            }
        }
    }
}


@Composable
fun CastMediaSection(castList: List<Cast>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, top = 22.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cast",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = netflixFamily,
                fontSize = 18.sp
            )
        }
        LazyRow(
            modifier = Modifier.padding(
                start = 22.dp, end = 22.dp, top = 8.dp, bottom = 0.dp
            )
        ) {
            items(castList.size) { index ->
                CastMemberItem(castList[index])
            }
        }
    }


}

@Composable
fun CastMemberItem(cast: Cast) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        if (cast.profilePath != null) {
            Image(
                painter = rememberAsyncImagePainter(model = Constants.BASE_POSTER_IMAGE_URL + cast.profilePath),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.user), // Replace with your placeholder
                    contentDescription = "Cast Member Placeholder",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black), startY = 100f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = cast.name, style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontFamily = netflixFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            )
        }
    }


}

@Composable
fun DisplayMovieData(
    moviesInfo: MovieDetailsDTO?,
    navController: NavHostController,
    watchListViewModel: WatchListViewModel
) {
    watchListViewModel.exist(moviesInfo!!.id)
    var  exist = watchListViewModel.exist.value

    Log.e("TAG_exist_>", "DisplayMovieData: "+exist )
    var context = LocalContext.current
    val date = SimpleDateFormat.getDateInstance().format(Date())
    val myListMovie = WatchListModel(
        mediaId = moviesInfo!!.id,
        imagePath = moviesInfo.posterPath,
        title = moviesInfo.title,
        releaseDate = moviesInfo.releaseDate,
        rating = moviesInfo.voteAverage,
        addedOn = date
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {

            Image(painter = rememberAsyncImagePainter(Constants.BASE_BACKDROP_IMAGE_URL + moviesInfo!!.backdropPath),
                contentDescription = "Backdrop Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .graphicsLayer {
                        alpha = 0.7f
                    })
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black), startY = 100f
                        )
                    )
            )
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(24.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(BASE_POSTER_IMAGE_URL + moviesInfo.posterPath),
                contentDescription = "Poster Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
                    .offset(y = 75.dp)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(8.dp))
            )
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd).padding(top = 16.dp, end = 4.dp),
                onClick = {
                if (exist != 0) {
                    watchListViewModel.removeFromWatchList(mediaId = moviesInfo.id)
                    Toast.makeText(
                        context,
                        "Remove From your Watch List",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    watchListViewModel.addToWatchList(myListMovie)
                    Toast.makeText(
                        context,
                        "Added to your Watch List",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(
                        context,
                        "Added to your Watch List",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Icon(
                    imageVector = if (exist != 0) {
                        Icons.Default.Bookmark
                    } else {
                        Icons.Default.BookmarkBorder
                    },
                    tint = Color.White,
                    contentDescription = "Review"
                )
            }

        }
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = moviesInfo!!.title,
            fontFamily = netflixFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                modifier = Modifier.align(Alignment.Center),
                horizontalArrangement = Arrangement.Center
            ) {
                items(moviesInfo.genres.size) { index ->
                    GenreChip(genre = moviesInfo.genres[index].name)
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            ) {
                val context = LocalContext.current
                MovieField(context.getString(R.string.release_date), moviesInfo.releaseDate)
                MovieField("Duration", moviesInfo?.runtime.toString() + " min.")
                MovieField("Rating", "⭐ " + moviesInfo.voteAverage)
                MovieField("Language", moviesInfo.spokenLanguages[0].name)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        moviesInfo.overview?.let { OverviewSection(it, moviesInfo.tagline) }

    }
}

@Composable
fun OverviewSection(overview: String, tagline: String?) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = tagline!!,
            fontFamily = netflixFamily,
            fontSize = 17.sp,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = overview,
            fontFamily = netflixFamily,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 16.sp
        )

    }
}

@Composable
fun GenreChip(genre: String) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(2.dp, Color.White),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            text = genre,
            color = Color.White,
            fontFamily = netflixFamily,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(8.dp),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun MovieField(name: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            fontFamily = netflixFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 1.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = value,
            fontFamily = netflixFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
        )
    }
}

@Composable
fun SimilarMediaSection(
    media: MovieResponse,
    navController: NavHostController,
) {
    val mediaList = media.results
    if (mediaList.isNotEmpty()) Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, top = 22.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Similar Movies",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = netflixFamily,
                fontSize = 18.sp
            )


        }
        LazyRow(
            modifier = Modifier.padding(
                start = 22.dp, end = 22.dp, top = 8.dp, bottom = 16.dp
            )
        ) {
            items(media.results.size) {
                HomeSmallThumb(
                    BASE_POSTER_IMAGE_URL + mediaList[it].posterPath
                ) {
                    navController.navigate(MovieAppScreen.MOVIE_HOME_DETAILS.route + "/${mediaList[it].id}")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen2() {


}

