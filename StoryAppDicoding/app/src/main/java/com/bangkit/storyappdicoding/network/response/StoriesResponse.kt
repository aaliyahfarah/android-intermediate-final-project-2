package com.bangkit.storyappdicoding.network.response

import com.bangkit.storyappdicoding.database.StoryEntity
import com.google.gson.annotations.SerializedName

data class StoriesResponse(

    @field:SerializedName("listStory")
    val listStory: List<StoryResponseItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class StoryResponseItem(

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Double

) {
    fun toEntity(): StoryEntity {
        return StoryEntity(
            this.id,
            this.name,
            this.description,
            this.photoUrl,
            this.createdAt,
            this.lat,
            this.lon,
        )
    }
}
