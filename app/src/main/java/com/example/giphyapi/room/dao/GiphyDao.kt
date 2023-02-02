package com.example.giphyapi.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giphyapi.room.GiphyEntity

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addList(list: List<GiphyEntity>)

    @Query("SELECT * FROM giphyEntity")
    fun getAll(): List<GiphyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGiphy(list: GiphyEntity)

}