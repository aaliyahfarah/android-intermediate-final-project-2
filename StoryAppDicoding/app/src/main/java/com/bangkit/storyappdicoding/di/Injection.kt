package com.bangkit.storyappdicoding.di

import android.content.Context
import com.bangkit.storyappdicoding.data.StoryRepository
import com.bangkit.storyappdicoding.database.StoryDatabase
import com.bangkit.storyappdicoding.network.api.ApiConfig

object Injection {
    fun provideRepository(context: Context) : StoryRepository {
        val database = StoryDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}