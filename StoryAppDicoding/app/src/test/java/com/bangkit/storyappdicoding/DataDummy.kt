package com.bangkit.storyappdicoding

import com.bangkit.storyappdicoding.database.StoryEntity

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryEntity> {
        val items: MutableList<StoryEntity> = arrayListOf()
        for (i in 0..100) {
            val story = StoryEntity(
                i.toString(),
                "user $i",
                "testtttttttthalo halo lorem ipsum",
                "https://o-cdn-cas.sirclocdn.com/parenting/images/tips-foto-aesthetic.width-800.format-webp.webp",
                "2023-06-29T14:53.7072",
                1.50,
                1.50
            )
            items.add(story)
        }
        return items
    }
}