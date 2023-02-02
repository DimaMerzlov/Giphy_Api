package com.example.giphyapi.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.giphyapi.room.GiphyEntity
import com.example.giphyapi.room.dao.GiphyDao

@Database(entities = [GiphyEntity::class], version = 1)
abstract class GiphyDataBase: RoomDatabase() {
    abstract fun giphyDao(): GiphyDao
}