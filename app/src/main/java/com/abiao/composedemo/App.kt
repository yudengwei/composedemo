package com.abiao.composedemo

import android.app.Application
import com.abiao.lib_nowinandroid.NowInAndroidApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NowInAndroidApplication.onCreate(this)
    }
}