package com.bangkit.storyappdicoding.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(stories: List<StoryEntity>)

    @Query("DELETE FROM stories")
    suspend fun deleteAllStories()

    @Query("SELECT * FROM stories ORDER BY createdAt DESC")
    fun getAllStories(): PagingSource<Int, StoryEntity>
}