package com.abiao.sync.work.initializer

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.abiao.sync.work.works.SyncWorker

object Sync {

    fun initializer(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                SyncWorker.startUpSyncWork(),
            )
        }
    }
}

internal const val SYNC_WORK_NAME = "SyncWorkName"