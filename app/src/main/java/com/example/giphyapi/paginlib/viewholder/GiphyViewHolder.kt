package com.example.giphyapi.paginlib.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphyapi.R
import com.example.giphyapi.databinding.ItemLayoutBinding
import com.example.giphyapi.model.ResponseGiphy


class GiphyViewHolder private constructor(private val binding: ItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var responseGiphy: ResponseGiphy? = null
        private set

    fun bindTo(
        item: ResponseGiphy?,
        clickListener: ((ResponseGiphy) -> Unit)?,

        ) {

        if (item is ResponseGiphy) {
            //binding.notification = item
            Log.d("TEGURL", item.images.original.url.toString())
            Glide.with(binding.root.context)
                .asGif()
                .load(item.images.original.url).error(
                R.drawable.ic_launcher_background
            ).into(binding.ivGif)
            binding.root.setOnClickListener {
                clickListener?.invoke(item)
            }

        }
    }

    companion object {

        fun from(parent: ViewGroup): GiphyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
            return GiphyViewHolder(binding)
        }
    }
}