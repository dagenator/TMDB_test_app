package com.example.tmdb_test_app.presenter.utils

import android.content.Context
import android.widget.ImageView
import com.example.tmdb_test_app.R
import com.example.tmdb_test_app.data.models.Config
import com.example.tmdb_test_app.data.models.Movie
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import javax.inject.Inject

class ImageUtils @Inject constructor(val config: Config) {
    fun loadImage(imageView: ImageView, url:String, context: Context?){
        val imageLoader = ImageLoader.getInstance()
        imageLoader.init(ImageLoaderConfiguration.createDefault(context))
        val displayOptions = DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.no_image)
            .showImageForEmptyUri(R.drawable.no_image)
            .cacheInMemory(true).build()
        val fullUrl = config.imageUrl + url
        imageLoader.displayImage(fullUrl, imageView, displayOptions, null)
    }
}