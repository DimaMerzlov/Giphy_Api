package com.example.giphyapi.network.api

import com.example.giphyapi.model.ListResponseGiphy
import com.example.giphyapi.model.ResponseGiphy
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    companion object {
        const val ENDPOINT = "https://api.giphy.com/v1/"
    }

    @GET("gifs/search?")
    suspend fun getData(@Query("api_key") api_key: String? = null,
                @Query("q") q: String? = null,
                @Query("limit") limit: Int? = null,
                @Query("offset") offset: Int? = null,
                @Query("rating") rating: String? = null,
                @Query("lang") lang: String? = null,
    ): Response<ListResponseGiphy>

    @GET("gifs/search?")
    fun getDataSearch(@Query("api_key") api_key: String? = null,
                @Query("q") q: Int? = null,
                @Query("limit") limit: Int? = null,
                @Query("offset") offset: Int? = null,
                @Query("rating") rating: String? = null,
                @Query("lang") lang: String? = null,
    ): Call<ListResponseGiphy>

}