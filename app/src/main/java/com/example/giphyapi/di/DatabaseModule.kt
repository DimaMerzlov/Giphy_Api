package com.example.giphyapi.di

import android.content.Context
import androidx.room.Room
import com.example.giphyapi.room.dao.GiphyDao
import com.example.giphyapi.room.database.GiphyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: GiphyDataBase): GiphyDao {
        return appDatabase.giphyDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GiphyDataBase {
        return Room.databaseBuilder(
            appContext,
            GiphyDataBase::class.java,
            "giphyDataBase"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}