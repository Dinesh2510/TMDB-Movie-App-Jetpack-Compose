package com.app.movieapp.data.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.movieapp.data.remote.response.MovieResponse
import com.app.movieapp.data.repository.HomeRepository
import com.app.movieapp.models.Movies
import com.app.movieapp.utlis.MovieState
import com.app.movieapp.utlis.NetworkUtils
import com.ericg.neatflix.data.remote.response.GenreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {


    private val _response2: MutableStateFlow<MovieState<MovieResponse?>> = MutableStateFlow(MovieState.Loading)
    val discoveryMovieResponses: StateFlow<MovieState<MovieResponse?>> = _response2

    private val _response3: MutableStateFlow<MovieState<MovieResponse?>> = MutableStateFlow(MovieState.Loading)
    val trendingMovieResponses: StateFlow<MovieState<MovieResponse?>> = _response3

    private val _response4: MutableStateFlow<MovieState<MovieResponse?>> = MutableStateFlow(MovieState.Loading)
    val nowPlayingMoviesResponses: StateFlow<MovieState<MovieResponse?>> = _response4

 private val _response5: MutableStateFlow<MovieState<MovieResponse?>> = MutableStateFlow(MovieState.Loading)
    val upcomingMoviesResponses: StateFlow<MovieState<MovieResponse?>> = _response5

    private val _response6: MutableStateFlow<MovieState<GenreResponse?>> = MutableStateFlow(MovieState.Loading)
    val genresMoviesResponses: StateFlow<MovieState<GenreResponse?>> = _response6

    var genresWiseMovieListState: Flow<PagingData<Movies>>? = null

    private val networkUtils = NetworkUtils()
    val networkType = networkUtils.networkType


    init {
        fetchDiscoverMovies()
        fetchTrendingMovies()
        fetchNowPlayingMovies()
        fetchUpcomingMovies()
        fetchGenreResponse()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun registerNetwork(context: Context) {
        networkUtils.registerNetworkCallback(context)
    }



    //Pagination
    val nowPlayingAllListState = repository.getAllMoviesPagination("nowPlayingAllListScreen").flow.cachedIn(viewModelScope)
    val popularAllListState = repository.getAllMoviesPagination("popularAllListScreen").flow.cachedIn(viewModelScope)
    val discoverListState = repository.getAllMoviesPagination("discoverListScreen").flow.cachedIn(viewModelScope)
    val upcomingListState = repository.getAllMoviesPagination("upcomingListScreen").flow.cachedIn(viewModelScope)
    //val genresWiseMovieListState = repository.getGenresWiseMovieRepo(genre_id).flow.cachedIn(viewModelScope)

    fun setGenreData(genreSelected: Int) {
        genresWiseMovieListState=  repository.getGenresWiseMovieRepo(genreSelected).flow.cachedIn(viewModelScope)
    }



    fun fetchDiscoverMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getDiscoverMoviesRepo().first()
                _response2.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response2.emit(MovieState.Error(errorMessage))
            }
        }
    }
    fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getTrendingMoviesRepo().first()
                _response3.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response3.emit(MovieState.Error(errorMessage))
            }
        }
    }


    fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getNowPlayingMoviesRepo().first()
                _response4.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response4.emit(MovieState.Error(errorMessage))
            }
        }
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getUpcomingMoviesRepo().first()
                _response5.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response5.emit(MovieState.Error(errorMessage))
            }
        }
    }

    fun fetchGenreResponse() {
        viewModelScope.launch {
            try {
                val response = repository.getMovieGenresRepo().first()
                _response6.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response6.emit(MovieState.Error(errorMessage))
            }
        }
    }
}
    /*
    //this is LiveData Code
    private val _response: MutableLiveData<ResponseQuotes> = MutableLiveData()
    val quoteResponse: LiveData<ResponseQuotes> = _response
    init {
        fetchQuotes()
    }

    private fun fetchQuotes(){
        viewModelScope.launch {
            repository.getQuotesRepo()
                .catch {e->
                    Log.e("MainViewModel_ERROR", "getPost: ${e.message}")
                }.collect {response->
                    _response.value = response
                    // postLiveData.value=response
                }

        }
    }*/
