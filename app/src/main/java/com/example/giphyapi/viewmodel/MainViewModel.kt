package com.example.giphyapi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*

import com.example.giphyapi.model.ResponseGiphy
import com.example.giphyapi.network.api.ApiService
import com.example.giphyapi.paginlib.source.GiphyPagingSource
import com.example.giphyapi.paginlib.source.NETWORK_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    var currentQuery = MutableLiveData("")


    private fun getGiphyPager(searchText: String): Pager<Int, ResponseGiphy> {
        return Pager(
            config = PagingConfig(
                NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GiphyPagingSource(apiService, searchText)
            }
        )
    }

    fun getGiphyPagerLiveData(): LiveData<PagingData<ResponseGiphy>> {
        var defaultQuery = ""
        if (currentQuery.value?.isNotEmpty() == true) defaultQuery = currentQuery.value.toString()
        Log.d("defaultQuery", currentQuery.value.toString())
        return getGiphyPager(defaultQuery).liveData.cachedIn(viewModelScope)

    }

    var test = currentQuery.switchMap {
        getGiphyPager(it).liveData.cachedIn(viewModelScope)
    }
}