package com.app.movieapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.movieapp.R
import com.app.movieapp.data.viewmodel.WatchListViewModel
import com.app.movieapp.graph.MovieAppScreen
import com.app.movieapp.screens.Componets.DismissBackground
import com.app.movieapp.screens.Componets.SearchMovieCard
import com.app.movieapp.utlis.CenteredTopBar
import com.app.movieapp.utlis.Constants.Companion.BASE_POSTER_IMAGE_URL
import com.app.movieapp.utlis.SimpleLightTopAppBar


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SavedMovieScreen(
    watchListViewModel: WatchListViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val roomData = watchListViewModel.myMovieData.value.collectAsState(initial = emptyList()).value
    Scaffold(topBar = {
        CenteredTopBar("WatchList (${roomData.size})",
            { navController.navigate(MovieAppScreen.MOVIE_SEARCH.route) },
            { navController.navigate(MovieAppScreen.MOVIE_HOME.route) })
    }) { padding: PaddingValues ->
        if (roomData.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                /*Image(
                    painter = painterResource(id = R.drawable.no_data),
                    contentDescription = "No data"
                )*/
                Text(
                    text = "No Data in WatchList",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(roomData, key = { it.mediaId }) {
                    val movie = it
                    val imageUrl = movie?.imagePath?.let { BASE_POSTER_IMAGE_URL + it }
                    val context = LocalContext.current
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            when (it) {

                                SwipeToDismissBoxValue.EndToStart -> {
                                    watchListViewModel.removeFromWatchList(movie.mediaId)
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT)
                                        .show()
                                }

                                SwipeToDismissBoxValue.Settled -> {
                                    return@rememberSwipeToDismissBoxState false
                                }

                                SwipeToDismissBoxValue.StartToEnd -> {

                                }
                            }
                            return@rememberSwipeToDismissBoxState true
                        },
                        // positional threshold of 25%
                        positionalThreshold = { it * .25f }
                    )
                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = { DismissBackground(dismissState) },
                        content = {
                            SearchMovieCard(
                                imageUrl = imageUrl ?: "",
                                title = movie.title ?: "",
                                overview = movie.releaseDate ?: ""
                            ) {
                                navController.navigate(MovieAppScreen.MOVIE_HOME_DETAILS.route + "/${movie.mediaId}")
                            }
                        })

                }
            }
        }
    }

}