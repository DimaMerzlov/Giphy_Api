package com.example.giphyapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.giphyapi.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    var currentQuery = MutableLiveData("")

    var getGiphyPagerLiveData = currentQuery.switchMap {
        giphyRepository.getGiphyPager(it).liveData.cachedIn(viewModelScope)
    }

    fun getAllFromRoom(){
        giphyRepository.getGiphyFromRoom()
    }

    fun deleteAll(){
        giphyRepository.deleteAll()
    }
}