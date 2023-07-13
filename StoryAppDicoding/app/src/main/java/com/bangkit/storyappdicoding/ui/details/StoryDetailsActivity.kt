package com.bangkit.storyappdicoding.ui.details

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.storyappdicoding.databinding.ActivityStoryDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class StoryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryDetailsBinding
    private val storyDetailsViewModel by viewModels<StoryDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        val name = intent.getStringExtra(EXTRA_NAME)
        val photoUrl = intent.getStringExtra(EXTRA_PHOTO)

        binding.tvDetailName.text = name

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

        Glide.with(this)
            .load(photoUrl)
            .apply(requestOptions)
            .into(binding.ivDetailPhoto)

        id?.let { storyDetailsViewModel.getDetails(it) }

        storyDetailsViewModel.storyDetailsResponse.observe(this) {

            if(it != null) {
                binding.tvDetailDescription.text = it.story.description
            }
        }

        storyDetailsViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_NAME = "story_name"
        const val EXTRA_ID = "story_id"
        const val EXTRA_PHOTO = "story_photo"
    }
}