package com.app.movieapp.data.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.movieapp.data.local.WatchListModel
import com.app.movieapp.data.repository.MyListMovieRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val myListMovieRepository: MyListMovieRepository) :
    ViewModel() {
    private var _exist = mutableStateOf(0)
    val exist: State<Int> = _exist

    private val _myMovieData = mutableStateOf<Flow<List<WatchListModel>>>(emptyFlow())
    val myMovieData: State<Flow<List<WatchListModel>>> = _myMovieData

    init {
        allMovieData()
    }

    private fun allMovieData() {
       _myMovieData.value = myListMovieRepository.getAllData()
    }

    fun exist(mediaId: Int) {
        viewModelScope.launch {
            _exist.value = myListMovieRepository.exist(mediaId = mediaId)
        }
    }

    fun addToWatchList(movie: WatchListModel) {
        viewModelScope.launch {
            myListMovieRepository.insertMovieInList(movie)
        }.invokeOnCompletion {
            exist(movie.mediaId)
        }
    }

    fun removeFromWatchList(mediaId: Int) {
        viewModelScope.launch {
            myListMovieRepository.removeFromList(mediaId = mediaId)
        }.invokeOnCompletion {
            exist(mediaId = mediaId)
        }
    }

}