package com.abiao.sync.work.works

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlin.reflect.KClass

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HiltWorkerFactoryEntryPoint {
    fun hiltWorkFactory(): HiltWorkerFactory
}

private const val WORKER_CLASS_NAME = "RouterWorkerDelegateClassName"

internal fun KClass<out CoroutineWorker>.delegateData() =
    Data.Builder()
        .putString(WORKER_CLASS_NAME, qualifiedName)
        .build()

class DelegatingWorker(
    context: Context,
    parameters: WorkerParameters
): CoroutineWorker(context, parameters) {

    private val workClassName =
        parameters.inputData.getString(WORKER_CLASS_NAME) ?: ""

    private val delegatingWorker =
        EntryPointAccessors.fromApplication<HiltWorkerFactoryEntryPoint>(context)
            .hiltWorkFactory()
            .createWorker(context, workClassName, parameters)
     as? CoroutineWorker ?: throw IllegalArgumentException("Unable to find appropriate worker")

    override suspend fun doWork() =
        delegatingWorker.doWork()

    override suspend fun getForegroundInfo() =
        delegatingWorker.getForegroundInfo()
}