package com.bangkit.storyappdicoding.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bangkit.storyappdicoding.database.StoryDatabase
import com.bangkit.storyappdicoding.database.StoryEntity
import com.bangkit.storyappdicoding.network.api.ApiService

class StoryRepository (
    private val database: StoryDatabase,
    val apiService: ApiService
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getAllStories(): LiveData<PagingData<StoryEntity>> {

        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, apiService),
            pagingSourceFactory = {
                database.storyDao().getAllStories()
            }
        ).liveData
    }

    companion object {
    }
}