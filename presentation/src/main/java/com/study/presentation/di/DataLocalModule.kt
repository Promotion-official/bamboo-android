package com.study.presentation.di

import android.content.Context
import androidx.room.Room
import com.study.data.db.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataLocalModule {

    @Singleton
    @Provides
    fun providesDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PostDatabase::class.java,
        "algorithm_table"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun postDao(dataBase: PostDatabase) = dataBase.postDao()
}