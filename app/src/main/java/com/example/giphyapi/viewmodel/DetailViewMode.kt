package com.example.giphyapi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.giphyapi.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewMode @Inject constructor(
    private val giphyRepository: GiphyRepository
) : ViewModel() {

    fun deleteUrlFromCash(url: String) {
        giphyRepository.deleteGiphyFromCash(url)
    }


}