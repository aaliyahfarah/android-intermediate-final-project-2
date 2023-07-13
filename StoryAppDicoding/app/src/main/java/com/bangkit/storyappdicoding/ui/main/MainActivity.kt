package com.bangkit.storyappdicoding.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.storyappdicoding.R
import com.bangkit.storyappdicoding.adapter.LoadingStateAdapter
import com.bangkit.storyappdicoding.adapter.StoryListAdapter
import com.bangkit.storyappdicoding.databinding.ActivityMainBinding
import com.bangkit.storyappdicoding.network.api.ApiConfig
import com.bangkit.storyappdicoding.ui.ViewModelFactory
import com.bangkit.storyappdicoding.ui.login.LoginActivity
import com.bangkit.storyappdicoding.ui.maps.MapsActivity
import com.bangkit.storyappdicoding.ui.upload.UploadStoryActivity

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        sharedPreferences = getSharedPreferences("app-data", MODE_PRIVATE)

        val auth = sharedPreferences.getString("authToken", null)
        if(auth != null) {
            ApiConfig.setAuth(auth)
        }

        val layoutManager = LinearLayoutManager(this)
        binding?.rvStory?.layoutManager = layoutManager

        getData().also { Log.d(TAG, "getData()") }

        binding?.uploadStoryFAB?.setOnClickListener {
            val intent = Intent(this, UploadStoryActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getSupportActionBar()
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> {
                sharedPreferences.edit().clear().apply()
                ApiConfig.clearAuth()
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return true
            }
            R.id.action_maps -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun getData() {
        val adapter = StoryListAdapter().also { Log.d(TAG, "Memanggil adapter, $it")}
        binding?.rvStory?.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        mainViewModel.getAllStories().observe(this) {
            adapter.submitData(lifecycle, it).also { Log.d(TAG, "submitData") }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}