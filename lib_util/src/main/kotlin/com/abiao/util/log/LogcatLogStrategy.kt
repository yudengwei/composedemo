package com.abiao.util.log

import android.util.Log

/**
 * LogCat implementation for [LogStrategy]
 *
 * This simply prints out all logs to Logcat by using standard [Log] class.
 */
class LogcatLogStrategy : LogStrategy {
    override fun log(priority: Int, tag: String?, message: String) {
        var tag = tag
        Utils.checkNotNull(message)

        if (tag == null) {
            tag = DEFAULT_TAG
        }

        Log.println(priority, tag, message)
    }

    companion object {
        const val DEFAULT_TAG: String = "NO_TAG"
    }
}
