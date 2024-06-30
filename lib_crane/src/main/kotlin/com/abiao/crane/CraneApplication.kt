package com.abiao.crane

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.abiao.crane.util.UnsplashSizingInterceptor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CraneApplication : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(UnsplashSizingInterceptor)
            }
            .build()
    }
}