package com.bangkit.storyappdicoding.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.storyappdicoding.network.api.ApiConfig
import com.bangkit.storyappdicoding.network.response.StoryDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryDetailsViewModel : ViewModel() {

    private var _storyDetailsResponse = MutableLiveData<StoryDetailsResponse?>()
    val storyDetailsResponse: LiveData<StoryDetailsResponse?> = _storyDetailsResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetails(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStoryDetails(id)
        client.enqueue(object : Callback<StoryDetailsResponse> {
            override fun onResponse(
                call: Call<StoryDetailsResponse>,
                response: Response<StoryDetailsResponse>
            ) {
                _isLoading.value = false

                val responseBody = response.body()
                _storyDetailsResponse.value = responseBody
            }

            override fun onFailure(call: Call<StoryDetailsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "StoryDetailsResponse"
    }
}