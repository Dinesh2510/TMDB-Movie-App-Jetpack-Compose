package com.app.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.movieapp.data.remote.ApiService
import com.app.movieapp.models.Search
import kotlinx.coroutines.delay

import retrofit2.HttpException
import java.io.IOException

class SearchFilmSource(
    private val api: ApiService,
    private val searchParams: String,
    private val includeAdult: Boolean
) : PagingSource<Int, Search>() {
    override fun getRefreshKey(state: PagingState<Int, Search>): Int? = state.anchorPosition
var error = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val nextPage = params.key ?: 1
            delay(3000L)
            /*error++
            if (error ==3)
                throw IOException("My Custom error here")*/
            val searchMovies = api.multiSearch(
                page = nextPage,
                searchParams = searchParams,
                includeAdult = includeAdult
            )
            LoadResult.Page(
                data = searchMovies.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (searchMovies.results.isEmpty()) null else searchMovies.page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}