package com.app.movieapp.graph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.movieapp.screens.GenreWiseMoviesScreen
import com.app.movieapp.screens.MovieDetailsScreen
import com.app.movieapp.screens.MovieHomeScreen
import com.app.movieapp.screens.SavedMovieScreen
import com.app.movieapp.screens.ScreenAbout
import com.app.movieapp.screens.SearchScreen
import com.app.movieapp.screens.SeeAllScreen
import com.app.movieapp.screens.SplashScreen

@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val MOVIE_ID_ARG = "movieId"
    val SeeAllTags = "seeAllTags"
    val genreId = "genId"
    val genreName = "genName"
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = MovieAppScreen.SPLASH.route
    ) {

        composable(route = MovieAppScreen.SPLASH.route) {
            SplashScreen(navController = navController)
        }
        composable(route = MovieAppScreen.MOVIE_HOME.route) {
            MovieHomeScreen(navController = navController)
        }
        composable(
            route = MovieAppScreen.MOVIE_HOME_DETAILS.route + "/{$MOVIE_ID_ARG}",
            arguments = listOf(navArgument(MOVIE_ID_ARG) { type = NavType.StringType })
        ) {
            Log.e("TAG_PASSED_KEY", "RootNavigation: " + it.arguments?.getString(MOVIE_ID_ARG))
            MovieDetailsScreen(navController, it.arguments?.getString(MOVIE_ID_ARG) ?: "1")
        }

        composable(
            route = MovieAppScreen.MOVIE_SEE_ALL.route + "/{$SeeAllTags}",
            arguments = listOf(navArgument(SeeAllTags) { type = NavType.StringType })
        ) {
            Log.e("TAG_PASSED_KEY_all", "RootNavigation: " + it.arguments?.getString(SeeAllTags))
            SeeAllScreen(it.arguments?.getString(SeeAllTags) ?: "1", navController)
        }

        composable(
            route = MovieAppScreen.MOVIE_GENRE_WISE.route + "/{$genreId}" + "/{$genreName}",
            arguments = listOf(
                navArgument(genreId) { type = NavType.StringType },
                navArgument(genreName) { type = NavType.StringType }
            )
        ) {
            GenreWiseMoviesScreen(
                it.arguments?.getString(genreId) ?: "1",
                it.arguments?.getString(genreName) ?: "",
                navController
            )
        }

        composable(route = MovieAppScreen.MOVIE_SEARCH.route) {
            SearchScreen(navController = navController)
        }
        composable(route = MovieAppScreen.MOVIE_WATCHLIST.route) {
            SavedMovieScreen(navController = navController)
        }
        composable(route = MovieAppScreen.MOVIE_ABOUT.route) {
            ScreenAbout()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
}


sealed class MovieAppScreen(val route: String) {
    object SPLASH : MovieAppScreen(route = "splash")
    object MOVIE_HOME : MovieAppScreen(route = "home")
    object MOVIE_HOME_DETAILS : MovieAppScreen(route = "homeDetails")
    object MOVIE_SEE_ALL : MovieAppScreen(route = "seeAll")
    object MOVIE_GENRE_WISE : MovieAppScreen(route = "genreWiseMovie")
    object MOVIE_SEARCH : MovieAppScreen(route = "search")
    object MOVIE_ABOUT : MovieAppScreen(route = "about")
    object MOVIE_WATCHLIST : MovieAppScreen(route = "watch")
}