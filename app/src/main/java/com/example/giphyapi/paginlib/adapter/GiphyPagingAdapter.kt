package com.example.giphyapi.paginlib.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.giphyapi.paginlib.viewholder.GiphyViewHolder
import com.example.giphyapi.model.ResponseGiphy

class GiphyPagingAdapter : PagingDataAdapter<ResponseGiphy, GiphyViewHolder>(diffCallback) {
    var clickListener: ((ResponseGiphy) -> Unit)? = null

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<ResponseGiphy>() {
            override fun areItemsTheSame(oldItem: ResponseGiphy, newItem: ResponseGiphy): Boolean {
              return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseGiphy,
                newItem: ResponseGiphy
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        holder.bindTo(getItem(position),clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        return GiphyViewHolder.from(parent)
    }
}