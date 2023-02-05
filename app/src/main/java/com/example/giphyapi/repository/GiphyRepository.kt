package com.example.giphyapi.repository

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.giphyapi.model.ResponseGiphy
import com.example.giphyapi.network.api.ApiService
import com.example.giphyapi.paginlib.source.GiphyPagingSource
import com.example.giphyapi.paginlib.source.NETWORK_PAGE_SIZE
import com.example.giphyapi.room.entity.GiphyEntity
import com.example.giphyapi.room.dao.GiphyDao
import com.example.giphyapi.room.entity.GiphyEntityDelete
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: GiphyDao,
    @ApplicationContext private val context: Context
) {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)


    fun getGiphyPager(searchText: String): Pager<Int, ResponseGiphy> {
        return Pager(
            config = PagingConfig(
                NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GiphyPagingSource(apiService, searchText, saveListener = {
                    saveToCash(it)
                    saveToRoom(it)
                }) {
                    loadFromCash()
                }
            }
        )
    }

    private fun saveToRoom(list: List<ResponseGiphy>) {
        for (giphy in list) {

            var url = giphy.images?.original?.url
            if (!url?.let { isUrlInRoom(it) }!!)
            url?.let {
                GiphyEntity(
                    url.hashCode().toString(),
                    it
                )
            }?.let {
                dao.addGiphy(
                    it
                )
            }
        }
        Log.d("Glide in work", "room size ${dao.getAll().size}")

    }

    private fun saveToCash(list: List<ResponseGiphy>) {

        Log.d("Glide in work", "its worked ${list.size}")
        try {
            for (singleUrl in list) {
                var url = singleUrl.images?.original?.url
                if (url != null) {
                    if (!dao.getAllDeletedGiphy().contains(GiphyEntityDelete(url.hashCode().toString(),url))){
                        Glide.with(context).asFile().load(singleUrl.images?.original?.url).apply(
                            RequestOptions()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                        )
                            .into(object : SimpleTarget<File>() {
                                override fun onResourceReady(
                                    resource: File,
                                    transition: Transition<in File>?
                                ) {
                                    singleUrl.images?.original?.let { storeImage(resource, it.url) };
                                    Log.d("Glide in work", "its worked")
                                }

                            })
                    }
                }

                /*if (dao.getAllDeletedGiphy().contains() {

                }else*/
            }
        } catch (e: java.lang.Exception) {
            Log.d("Exepttion", e.toString())
        }

    }

    private fun storeImage(image: File, url: String) {
        scope.launch(Dispatchers.IO) {

            val dir = File(context.cacheDir, "mydir")
            if (!dir.exists()) {
                dir.mkdir()
            }
            val gpxfile = File(dir, url.hashCode().toString())
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(gpxfile)
                fos.write(image.readBytes())

            } catch (e: FileNotFoundException) {
                Log.d("Exepttion", e.toString())
                e.printStackTrace()
            } catch (e: IOException) {
                Log.d("Exepttion", e.toString())
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun getGiphyFromRoom() {
        Log.d("TEGROOM", dao.getAll().toString())
    }

    fun loadFromCash(): List<ResponseGiphy> {
        var listResponseGiphy = mutableListOf<ResponseGiphy>()
        for (giphyEntity in dao.getAll()) {
            val dir = File(context.cacheDir, "mydir")

            val gpxfile = File(dir, giphyEntity.hashCode)
            listResponseGiphy.add(ResponseGiphy(file = gpxfile))
        }
        return listResponseGiphy
    }

    fun deleteGiphyFromCash(url: String) {
        if (url.isNotEmpty()) {
            dao.addDeletedGiphy(GiphyEntityDelete(url.hashCode().toString(), url))
            dao.deleteGiphy(GiphyEntity(url.hashCode().toString(), url))
            val dir = File(context.cacheDir, "mydir")
            if (!dir.exists()) {
                dir.mkdir()
            }
            File(dir, url.hashCode().toString()).delete()
        }
    }

    fun deleteAll() {
        dao.deleteAll()
    }

    private fun isUrlInRoom(url: String): Boolean {
        for (giphyEntity in dao.getAll()) {
            if (giphyEntity.giphyUrl.equals(url)) {
                Log.d("Glide in work", "match")
                return true
            }
        }
        return false
    }
    private fun isUrlInDeletedRoom(url: String): Boolean {
        for (giphyEntity in dao.getAllDeletedGiphy()) {
            if (giphyEntity.giphyUrl.equals(url)) {
                Log.d("Glide in work", "match")
                return true
            }
        }
        return false
    }

}