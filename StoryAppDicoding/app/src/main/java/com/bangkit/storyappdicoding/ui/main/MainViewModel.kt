package com.bangkit.storyappdicoding.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.storyappdicoding.data.StoryRepository
import com.bangkit.storyappdicoding.database.StoryEntity

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getAllStories(): LiveData<PagingData<StoryEntity>> =
        storyRepository.getAllStories().cachedIn(viewModelScope)

}