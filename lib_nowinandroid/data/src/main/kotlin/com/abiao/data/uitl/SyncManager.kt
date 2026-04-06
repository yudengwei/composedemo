package com.abiao.data.uitl

import kotlinx.coroutines.flow.Flow

interface SyncManager {

    val isSyncing: Flow<Boolean>

    fun requestSync()
}