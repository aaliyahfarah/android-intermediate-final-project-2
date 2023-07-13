package com.bangkit.storyappdicoding.ui.maps

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.bangkit.storyappdicoding.R
import com.bangkit.storyappdicoding.network.response.StoryResponseItem
import com.bangkit.storyappdicoding.utils.toSnippetPreview
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(val context: Context, val hashMap: MutableMap<String, StoryResponseItem>) : GoogleMap.InfoWindowAdapter {
    private val window: View = LayoutInflater.from(context).inflate(R.layout.info_window_custom, null)

    private fun renderWindowText(marker: Marker, view: View) {
        Log.d("CustomInfoWindowAdapter", context.toString())
        val title = marker.title
        val tvTitle = view.findViewById<TextView>(R.id.tv_infowindow_name)
        if(!title.equals("")) {
            tvTitle.text = title
        }

        val key = marker.snippet
        val storyResponseItem = hashMap[key]
        val tvDescription = view.findViewById<TextView>(R.id.tv_infowindow_description)
        if(storyResponseItem != null) {
            tvDescription.text = toSnippetPreview(storyResponseItem.description)
        }
    }

    override fun getInfoContents(p0: Marker): View? {
        return null
    }

    override fun getInfoWindow(p0: Marker): View {
        renderWindowText(p0, window)
        return window
    }
}