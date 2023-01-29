package com.example.giphyapi.ui

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.giphyapi.paginlib.adapter.GiphyPagingAdapter
import com.example.giphyapi.databinding.ActivityMainBinding
import com.example.giphyapi.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var giphyAdapter = GiphyPagingAdapter().apply {
            clickListener = {

            }
        }

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
            viewModel.test.observe(this@MainActivity) {
                giphyAdapter.submitData(lifecycle, it)
            }
        }
    }
}