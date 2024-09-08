package com.app.movieapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.movieapp.data.viewmodel.SearchViewModel
import com.app.movieapp.graph.MovieAppScreen
import com.app.movieapp.screens.Componets.ErrorStrip
import com.app.movieapp.screens.Componets.SearchMovieCard
import com.app.movieapp.utlis.CenteredCircularProgressIndicator
import com.app.movieapp.utlis.Constants.Companion.BASE_POSTER_IMAGE_URL


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val searchResult = searchViewModel.multiSearchState.value.collectAsLazyPagingItems()
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        topBar = {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "",
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth().padding(top = 8.dp)
                        .semantics { isTraversalGroup = true }
                ) {
                    SearchBar(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .semantics { traversalIndex = 0f },
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = {
                            if (!text.isEmpty()) {
                                searchViewModel.searchParam.value = text
                                searchViewModel.searchRemoteMovie(true)
                                expanded = false
                            } else {
                                Toast.makeText(context, "Enter the text", Toast.LENGTH_LONG).show()
                            }
                        },
                        placeholder = { Text("Enter the text") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
                        active = expanded,
                        onActiveChange = {
                           // expanded = it
                        }
                    )
                    {
                      /*  Column(Modifier.verticalScroll(rememberScrollState())) {
                            repeat(4) { idx ->
                                val resultText = "Suggestion $idx"
                                ListItem(
                                    headlineContent = { Text(resultText) },
                                    supportingContent = { Text("Additional info") },
                                    leadingContent = {
                                        Icon(
                                            Icons.Filled.Star,
                                            contentDescription = null
                                        )
                                    },
                                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                    modifier = Modifier
                                        .clickable {
                                            text = resultText
                                            expanded = false
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                            }
                        }*/
                    }

                }
            }

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(searchResult.itemCount) { index ->
                    val movie = searchResult[index]
                    val imageUrl = movie?.posterPath?.let { BASE_POSTER_IMAGE_URL + it }
                    SearchMovieCard(
                        imageUrl = imageUrl ?: "",
                        title = movie?.title ?: "",
                        overview = movie?.overview ?: ""
                    ){
                        navController.navigate(MovieAppScreen.MOVIE_HOME_DETAILS.route + "/${movie?.id}")
                    }
                }

                searchResult.apply {
                    when {
                        // Handling the initial loading state
                        loadState.refresh is LoadState.Loading -> {
                            item { CenteredCircularProgressIndicator() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { CenteredCircularProgressIndicator() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val e = searchResult.loadState.refresh as LoadState.Error
                            item {
                                ErrorStrip(message = e.error.message ?: "Unknown Error")
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val e = searchResult.loadState.append as LoadState.Error
                            item {
                                ErrorStrip(message = e.error.message ?: "Unknown Error")
                            }
                        }
                    }
                }

            }
        }
    }

}
