package com.app.movieapp.data.remote

import com.app.movieapp.data.remote.response.MovieDetailsDTO
import com.ericg.neatflix.data.remote.response.CastResponse
import com.app.movieapp.data.remote.response.MovieResponse
import com.ericg.neatflix.data.remote.response.GenreResponse
import com.app.movieapp.data.remote.response.MultiSearchResponse
import com.app.movieapp.utlis.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    /****Movies***/
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("discover/movie")
    suspend fun getGenreWiseMovieList(
        @Query("with_genres") genresId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") filmId: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("discover/movie?")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int = 0,
        @Query("primary_release_date.gte") gteReleaseDate: String = "1940-01-01",
        @Query("primary_release_date.lte") lteReleaseDate: String = "1981-01-01",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
        @Query("sort_by") sortBy: String = "vote_count.desc"
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMoviesDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "videos,credits",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
    ): MovieDetailsDTO
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") filmId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): CastResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): GenreResponse

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") searchParams: String,
        @Query("page") page: Int = 0,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): MultiSearchResponse

    /** **Tv Shows**
     * CURRENTLY NOT IN USED
     * [Note]: **MovieResponse** and **TvShowResponse** attributes are combined based
     * on the fact that you can extract them at runtime by fetching what you need!*/

    @GET("genre/tv/list")
    suspend fun getTvShowGenres(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): GenreResponse

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCast(
        @Path("tv_id") filmId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): CastResponse

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("trending/tv/week")
    suspend fun getTrendingTvSeries(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse


    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse


    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendedTvShows(
        @Path("tv_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("discover/tv?")
    suspend fun getDiscoverTvShows(
        @Query("page") page: Int = 0,
        @Query("first_air_date.gte") gteFirstAirDate: String = "1940-01-01",
        @Query("first_air_date.lte") lteFirstAirDate: String = "1981-01-01",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "vote_count.desc"
    ): MovieResponse
}
