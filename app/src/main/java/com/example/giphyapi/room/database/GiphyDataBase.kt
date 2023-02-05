package com.example.giphyapi.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.giphyapi.room.entity.GiphyEntity
import com.example.giphyapi.room.dao.GiphyDao
import com.example.giphyapi.room.entity.GiphyEntityDelete

@Database(entities = [GiphyEntity::class, GiphyEntityDelete::class], version = 2)
abstract class GiphyDataBase : RoomDatabase() {
    abstract fun giphyDao(): GiphyDao
}