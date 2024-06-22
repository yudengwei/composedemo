package com.abiao.common.initlazy

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.abiao.util.log.AndroidLogAdapter
import com.abiao.util.log.FormatStrategy
import com.abiao.util.log.Logger
import com.abiao.util.log.PrettyFormatStrategy

class LogInitInitializer : Initializer<String> {
    override fun create(context: Context): String {
        val formatStrategy  = PrettyFormatStrategy.newBuilder()
            .tag("yudengwei")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        return "Log init success"
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}