package com.app.movieapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.movieapp.data.remote.response.MovieDetailsDTO
import com.app.movieapp.data.remote.response.MovieResponse
import com.app.movieapp.data.repository.MovieDetailsRepository
import com.app.movieapp.models.Cast
import com.app.movieapp.utlis.MovieState
import com.ericg.neatflix.data.remote.response.CastResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieDetailsRepository) :
    ViewModel() {
    private val _response: MutableStateFlow<MovieState<MovieDetailsDTO?>> =
        MutableStateFlow(MovieState.Loading)
    val detailsMovieResponses: StateFlow<MovieState<MovieDetailsDTO?>> = _response


    private val _response2: MutableStateFlow<MovieState<MovieResponse?>> =
        MutableStateFlow(MovieState.Loading)
    val similarMovieResponses: StateFlow<MovieState<MovieResponse?>> = _response2

    private val _response3: MutableStateFlow<MovieState<List<Cast>?>> =
        MutableStateFlow(MovieState.Loading)
    val castMovieResponses: StateFlow<MovieState<List<Cast>?>> = _response3


    fun fetchMoviesDetails(movieId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMoviesDetailsRepo(movieId).first()
                _response.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response.emit(MovieState.Error(errorMessage))
            }
        }
    }

    fun fetchSimilarMovies(movieId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSimilarMoviesRepo(movieId).first()
                _response2.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response2.emit(MovieState.Error(errorMessage))
            }
        }
    }

    fun fetchCasteOfMovies(movieId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCastMoviesRepo(movieId).first()

                // Assuming response contains a list of cast members (adjust based on your API structure)
                val castList = response.castResult ?: emptyList()
                _response3.emit(MovieState.Success(castList)) // Emit list of cast in Success state

            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response3.emit(MovieState.Error(errorMessage))
            }
        }
    }

}

