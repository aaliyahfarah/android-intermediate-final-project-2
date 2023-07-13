package com.bangkit.storyappdicoding.network.api

import com.bangkit.storyappdicoding.network.response.LoginResponse
import com.bangkit.storyappdicoding.network.response.RegisterResponse
import com.bangkit.storyappdicoding.network.response.StoriesResponse
import com.bangkit.storyappdicoding.network.response.StoryDetailsResponse
import com.bangkit.storyappdicoding.network.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("/v1/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/v1/register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>

    @GET("/v1/stories")
    suspend fun getStoriesWithPage(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): StoriesResponse

    @GET("/v1/stories")
    fun getStoriesWithSize(
        @Query("size") size: Int,
        @Query("location") location: Int = 1,
    ): Call<StoriesResponse>

    @GET("/v1/stories/{id}")
    fun getStoryDetails(
        @Path("id") id: String
    ): Call<StoryDetailsResponse>

    @Multipart
    @POST("/v1/stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<UploadResponse>

    @Multipart
    @POST("/v1/stories")
    fun uploadImageWithLoc(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody,
    ): Call<UploadResponse>
}