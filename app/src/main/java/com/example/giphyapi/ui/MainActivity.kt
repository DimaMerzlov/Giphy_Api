package com.example.giphyapi.ui

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.giphyapi.CheckNetworkConnection
import com.example.giphyapi.paginlib.adapter.GiphyPagingAdapter
import com.example.giphyapi.databinding.ActivityMainBinding
import com.example.giphyapi.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var checkNetworkConnection: CheckNetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var giphyAdapter = GiphyPagingAdapter().apply {
            clickListener = {
                var intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(URL, it.images?.original?.url)
                startActivity(intent)
            }
        }
        viewModel.deleteAll()

        callNetworkConnection()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.currentQuery.postValue(newText)
                return false
            }

        })

        binding.rvGiphy.adapter = giphyAdapter

        lifecycleScope.launch {
            viewModel.getGiphyPagerLiveData.observe(this@MainActivity) {
                giphyAdapter.submitData(lifecycle, it)
            }
        }

        /*var intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(URL, "it.images.original.url")
        startActivity(intent)*/

    }

    private fun callNetworkConnection() {
        /*checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this,{ isConnected ->
            if (isConnected){

            }else{
                //loadFromCache()
            }
        })*/

    }

    companion object {
        var URL = "gif url"
    }
}