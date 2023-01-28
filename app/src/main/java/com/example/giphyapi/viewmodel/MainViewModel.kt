package com.example.giphyapi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.giphyapi.model.ListResponseGiphy
import com.example.giphyapi.model.ResponseGiphy
import com.example.giphyapi.network.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val apiKey = "8OsLxpwWbFKLeRPumoLpUyM137qaTlf8"
    val q = 25
    val limit = 25
    val offset = 0
    val rating = "g"
    val lang = "lang"

    fun getTestData() {
            apiService.getData(api_key = apiKey, q, limit, offset, rating, lang)
                .enqueue(object : Callback<ListResponseGiphy> {
                    override fun onResponse(
                        call: Call<ListResponseGiphy>,
                        response: Response<ListResponseGiphy>
                    ) {
                        Log.d("TEGGG", response.body().toString())
                    }

                    override fun onFailure(call: Call<ListResponseGiphy>, t: Throwable) {
                        Log.d("TEGGG", t.toString())
                    }
                })
    }
}