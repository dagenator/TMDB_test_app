package com.example.tmdb_test_app.core.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb_test_app.core.retrofit.TMDBApiService
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie
import retrofit2.HttpException
import javax.inject.Inject

class PopularMoviesPageSource @Inject constructor(
    val tmdbApiService: TMDBApiService,
    val config: Config
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val page = params.key ?: 1
            val response = tmdbApiService.getPopularMovies(config.apiKey, page)
            val movies = response.results
            Log.i("length_tag", "getPopularMovies: ${movies.size}")
            val nextPageNumber = if (movies.isEmpty()) null else page + 1
            val prevPageNumber = if (page > 1) page - 1 else null
            return LoadResult.Page(movies, prevPageNumber, nextPageNumber)

        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 20
    }
}