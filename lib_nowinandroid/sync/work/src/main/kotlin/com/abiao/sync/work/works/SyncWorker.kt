package com.abiao.sync.work.works

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.abiao.common.di.coroutine.Dispatch
import com.abiao.common.di.coroutine.NiaDispatchers
import com.abiao.data.Synchronizer
import com.abiao.data.datastore.NiaUserPreferencesDataSource
import com.abiao.data.repository.TopicsRepository
import com.abiao.model.ChangeListVersion
import com.abiao.sync.work.initializer.SyncConstraints
import com.abiao.sync.work.initializer.syncForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val niaPreferences: NiaUserPreferencesDataSource,
    private val topicRepository: TopicsRepository,
    @Dispatch(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): CoroutineWorker(appContext, workerParams), Synchronizer {

    override suspend fun getForegroundInfo() =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        // First sync the repositories in parallel
        val syncedSuccessfully = awaitAll(
            async { topicRepository.sync() },
        ).all { it }


        if (syncedSuccessfully) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    override suspend fun getChangeListVersion() = niaPreferences.getChangeListVersions()
    override suspend fun updateChangeListVersion(update: ChangeListVersion.() -> ChangeListVersion) {
        niaPreferences.updateChangeListVersion(update)
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegateData())
            .build()
    }
}