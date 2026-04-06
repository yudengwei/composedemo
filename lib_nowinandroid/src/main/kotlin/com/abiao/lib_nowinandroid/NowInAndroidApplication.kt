package com.abiao.lib_nowinandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.abiao.sync.work.initializer.Sync

@SuppressLint("StaticFieldLeak")
object NowInAndroidApplication{

    private lateinit var mContext: Context

    fun onCreate(app: Application) {
        this.mContext = app
        Sync.initializer(app)
    }
}