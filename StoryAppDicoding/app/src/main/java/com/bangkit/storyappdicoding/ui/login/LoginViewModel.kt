package com.bangkit.storyappdicoding.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.storyappdicoding.network.api.ApiConfig
import com.bangkit.storyappdicoding.network.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage

    fun getLoginInfo(email: String, password: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false

                val responseBody = response.body()
                if(responseBody != null && !responseBody.error) {
                    _login.value = responseBody
                } else {
                    _errorMessage.value = INVALID_CREDENTIALS
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
                _errorMessage.value = LOGIN_FAILURE
            }

        })
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    companion object {
        const val TAG = "LoginViewModel"
        const val INVALID_CREDENTIALS = "invalid_credentials"
        const val LOGIN_FAILURE = "login_failure"
    }
}