package com.abiao.data

import android.util.Log
import com.abiao.model.ChangeListVersion
import com.abiao.network.model.NetworkChangeList
import kotlin.coroutines.cancellation.CancellationException

interface Synchronizer {

    suspend fun getChangeListVersion(): ChangeListVersion

    suspend fun updateChangeListVersion(update: ChangeListVersion.() -> ChangeListVersion)

    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}

private suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Log.i(
        "suspendRunCatching",
        "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result",
        exception,
    )
    Result.failure(exception)
}

suspend fun Synchronizer.changeListSync(
    versionReader: (ChangeListVersion) -> Int,
    changeListFetcher: suspend (Int) -> List<NetworkChangeList>,
    versionUpdater: ChangeListVersion.(Int) -> ChangeListVersion,
    modelDeleter: suspend (List<String>) -> Unit,
    modelUpdater: suspend (List<String>) -> Unit,
) = suspendRunCatching {
    // Fetch the change list since last sync (akin to a git fetch)
    val currentVersion = versionReader(getChangeListVersion())
    val changeList = changeListFetcher(currentVersion)
    if (changeList.isEmpty()) return@suspendRunCatching true

    val (deleted, updated) = changeList.partition(NetworkChangeList::isDelete)

    // Delete models that have been deleted server-side
    modelDeleter(deleted.map(NetworkChangeList::id))

    // Using the change list, pull down and save the changes (akin to a git pull)
    modelUpdater(updated.map(NetworkChangeList::id))

    // Update the last synced version (akin to updating local git HEAD)
    val latestVersion = changeList.last().changeListVersion
    updateChangeListVersion {
        versionUpdater(latestVersion)
    }
}.isSuccess