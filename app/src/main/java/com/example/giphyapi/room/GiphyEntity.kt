package com.example.giphyapi.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GiphyEntity(
    @PrimaryKey(autoGenerate = false) var hashCode: String,
    var giphyUrl: String
)
