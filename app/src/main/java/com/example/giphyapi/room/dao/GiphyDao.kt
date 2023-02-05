package com.example.giphyapi.room.dao

import androidx.room.*
import com.example.giphyapi.room.entity.GiphyEntity
import com.example.giphyapi.room.entity.GiphyEntityDelete

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addList(list: List<GiphyEntity>)

    @Query("SELECT * FROM giphyEntity")
    fun getAll(): List<GiphyEntity>

    @Query("DELETE FROM giphyEntity")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGiphy(giphy: GiphyEntity)

    @Delete()
    fun deleteGiphy(giphy: GiphyEntity)

    @Query("SELECT * FROM giphyEntityDelete")
    fun getAllDeletedGiphy(): List<GiphyEntityDelete>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeletedGiphy(giphy: GiphyEntityDelete)

}