package com.example.giphyapi.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GiphyEntityDelete (
    @PrimaryKey(autoGenerate = false) var hashCode: String,
    var giphyUrl: String
)