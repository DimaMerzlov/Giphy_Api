package com.example.giphyapi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.giphyapi.databinding.ActivityDetailBinding
import com.example.giphyapi.viewmodel.DetailViewMode
import dagger.hilt.android.AndroidEntryPoint
import java.io.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewMode>()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var url = intent.getStringExtra(MainActivity.URL)

        if (url?.isNotEmpty() == true) {
            Glide.with(this).load(url).into(binding.imageView)
        }

        binding.ivDelete.setOnClickListener {
            url.let {
                it
            }?.let { it1 -> viewModel.deleteUrlFromCash(it1) }
        }

        /*val dir = File(this@DetailActivity.cacheDir, "mydir")

        val gpxfile = File(dir, this.url.hashCode().toString())
        Glide.with(this).load(gpxfile).into(binding.imageView)*/


    }
}