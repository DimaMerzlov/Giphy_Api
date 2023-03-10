package com.example.giphyapi.paginlib.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.giphyapi.model.ResponseGiphy
import com.example.giphyapi.network.api.ApiService

private const val INITIAL_LOAD_SIZE = 1
const val NETWORK_PAGE_SIZE = 2


class GiphyPagingSource(
    private val service: ApiService,
    private val query: String,
    var saveListener: ((List<ResponseGiphy>) -> Unit),
    var loadListener: (() -> List<ResponseGiphy>),
    //var loadListener: (() -> Unit)
) : PagingSource<Int, ResponseGiphy>() {

    val apiKey = "8OsLxpwWbFKLeRPumoLpUyM137qaTlf8"
    val q = 25
    val limit = 2
    val offset = 0
    val rating = "g"
    val lang = "lang"


    override fun getRefreshKey(state: PagingState<Int, ResponseGiphy>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseGiphy> {
        return try {
            Log.d("TEGISEMPTY", "e.toString()")
            val position = params.key ?: INITIAL_LOAD_SIZE
            var query = this.query.ifEmpty { position.toString() }
            val jsonResponse =
                service.getData(apiKey, query, limit, offset, rating, lang)
            val response =
                if (jsonResponse.isSuccessful) jsonResponse.body()?.data else loadListener.invoke()
            //val response = jsonResponse.body()?.data
            val nextKey = if (response?.isEmpty() == true) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = if (response.isNullOrEmpty()) {
                    //loadListener.invoke()
                    //todo we can add to ResponseGiphy field File and if not have wifi pass File and show it in glide
                    Log.d("TEGISEMPTY", "nothing")
                    loadListener.invoke()
                } else {
                    saveListener.invoke(response)
                    Log.d("TEGISEMPTY", "saveListener")
                    response
                },
                prevKey = if (position == INITIAL_LOAD_SIZE) null else position - 1, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = if (response?.isEmpty() == true) null else position + 1
            )
        } catch (e: Exception) {
            Log.d("TEGISEMPTY", e.toString())
            LoadResult.Page(data = loadListener.invoke(), null, null)
        }
    }
}