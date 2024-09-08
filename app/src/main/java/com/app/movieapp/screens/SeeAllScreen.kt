package com.app.movieapp.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.movieapp.R
import com.app.movieapp.data.viewmodel.HomeViewModel
import com.app.movieapp.graph.MovieAppScreen
import com.app.movieapp.models.Movies
import com.app.movieapp.screens.Componets.ErrorStrip
import com.app.movieapp.screens.Componets.MovieItemSeeAll
import com.app.movieapp.utlis.CenteredTopBar
import com.app.movieapp.utlis.Constants.Companion.discoverListScreen
import com.app.movieapp.utlis.Constants.Companion.genreWiseMovie
import com.app.movieapp.utlis.Constants.Companion.nowPlayingAllListScreen
import com.app.movieapp.utlis.Constants.Companion.popularAllListScreen
import com.app.movieapp.utlis.Constants.Companion.upcomingListScreen


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SeeAllScreen(
    selectedTitle: String,
    navController: NavController,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    var allMoviesPagination: LazyPagingItems<Movies>? = null
    var title = ""
    BackHandler(
        enabled = true
    ) {
        navController.popBackStack()
    }

    when (selectedTitle) {
        nowPlayingAllListScreen -> {
            title = stringResource(id = R.string.nowPlaying)
            allMoviesPagination = viewModel.nowPlayingAllListState.collectAsLazyPagingItems()
        }

        discoverListScreen -> {
            title = stringResource(id = R.string.discover)
            allMoviesPagination = viewModel.discoverListState.collectAsLazyPagingItems()

        }

        upcomingListScreen -> {
            title = stringResource(id = R.string.upcoming)
            allMoviesPagination = viewModel.upcomingListState.collectAsLazyPagingItems()

        }

        popularAllListScreen -> {
            title = stringResource(id = R.string.popular)
            allMoviesPagination = viewModel.popularAllListState.collectAsLazyPagingItems()
        }

        else -> ""
    }
    Scaffold(
        topBar = {
            CenteredTopBar(title,
                { navController.navigate(MovieAppScreen.MOVIE_SEARCH.route) },
                { navController.navigate(MovieAppScreen.MOVIE_HOME.route) })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            val listState = rememberLazyGridState()
            LazyVerticalGrid(
                state = listState,
                contentPadding = PaddingValues(top = 16.dp),
                columns = GridCells.Adaptive(150.dp),
            ) {
                items(allMoviesPagination!!.itemCount) { i ->
                    allMoviesPagination?.get(i)?.let {
                        MovieItemSeeAll(
                            it,
                            navController
                        )
                    }
                }
                allMoviesPagination.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    contentAlignment = Center, modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    contentAlignment = Center, modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val e = allMoviesPagination.loadState.refresh as LoadState.Error
                            item { e.error.localizedMessage?.let { ErrorStrip(message = it) } }
                        }

                        loadState.append is LoadState.Error -> {
                            val e = allMoviesPagination.loadState.append as LoadState.Error
                            item { e.error.localizedMessage?.let { ErrorStrip(message = it) } }
                        }
                    }
                }
            }

        }


    }

}



fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GenreWiseMoviesScreen(
    genId: String,
    genName: String,
    navController: NavController,
) {
    val viewModel: HomeViewModel = hiltViewModel()

    viewModel.setGenreData(genId.toInt())

    var genresWiseMoviePagination: LazyPagingItems<Movies>? = null
    var title = ""
    BackHandler(
        enabled = true
    ) {
        navController.popBackStack()
    }

    title = genName
    genresWiseMoviePagination = viewModel.genresWiseMovieListState?.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            CenteredTopBar(title,
                { navController.navigate(MovieAppScreen.MOVIE_SEARCH.route) },
                { navController.navigate(MovieAppScreen.MOVIE_HOME.route) })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            val listState = rememberLazyGridState()
            LazyVerticalGrid(
                state = listState,
                contentPadding = PaddingValues(top = 16.dp),
                columns = GridCells.Adaptive(150.dp),
            ) {
                items(genresWiseMoviePagination!!.itemCount) { i ->
                    genresWiseMoviePagination?.get(i)?.let {
                        MovieItemSeeAll(
                            it,
                            navController
                        )
                    }
                }
                genresWiseMoviePagination.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    contentAlignment = Center, modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    contentAlignment = Center, modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val e = genresWiseMoviePagination.loadState.refresh as LoadState.Error
                            Log.e("TAG_LoadState", "GenreWiseMoviesScreen: "+ e.error)
                            item { e.error.localizedMessage?.let { ErrorStrip(message = it) } }
                        }

                        loadState.append is LoadState.Error -> {
                            val e = genresWiseMoviePagination.loadState.append as LoadState.Error
                            item { e.error.localizedMessage?.let { ErrorStrip(message = it) } }
                        }
                    }
                }
            }

        }


    }

}

