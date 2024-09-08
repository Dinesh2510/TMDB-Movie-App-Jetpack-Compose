package com.app.movieapp.data.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.app.movieapp.data.repository.SearchRepository
import com.app.movieapp.models.Search

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    private var _multiSearch = mutableStateOf<Flow<PagingData<Search>>>(emptyFlow())
    val multiSearchState: State<Flow<PagingData<Search>>> = _multiSearch


    var searchParam = mutableStateOf("")

    init {
        searchParam.value = "Jack Reacher"
        searchRemoteMovie(true)
    }

    fun searchRemoteMovie(includeAdult: Boolean) {
        viewModelScope.launch {
            if (searchParam.value.isNotEmpty()) {
                _multiSearch.value = searchRepository.multiSearch(
                    searchParams = searchParam.value,
                    includeAdult
                ).map { result ->
                    result.filter { ((it.title != null || it.originalName != null || it.originalTitle != null)) }
                }.cachedIn(viewModelScope)
            }
        }
    }
}