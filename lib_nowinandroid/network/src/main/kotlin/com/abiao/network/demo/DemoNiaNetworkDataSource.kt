package com.abiao.network.demo

import JvmUnitTestDemoAssetManager
import android.os.Build
import android.os.Build.VERSION_CODES.M
import com.abiao.common.di.coroutine.Dispatch
import com.abiao.common.di.coroutine.NiaDispatchers
import com.abiao.network.NiaNetworkDataSource
import com.abiao.network.model.NetworkChangeList
import com.abiao.network.model.NetworkTopic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.BufferedReader
import javax.inject.Inject
import kotlin.collections.mapIndexed

class DemoNiaNetworkDataSource @Inject constructor(
    @Dispatch(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val demoAssetManager: DemoAssetManager = JvmUnitTestDemoAssetManager
): NiaNetworkDataSource {
    override suspend fun getNetworkTopic(): List<NetworkTopic> = getDataFromJsonFile(TOPICS_ASSET)

    override suspend fun getNetworkChangeList(): List<NetworkChangeList> = getNetworkTopic().mapToChangeList(NetworkTopic::id)

    @OptIn(ExperimentalSerializationApi::class)
    private suspend inline fun <reified T> getDataFromJsonFile(fileName: String): List<T> =
        withContext(ioDispatcher) {
            demoAssetManager.open(fileName).use {inputStream ->
                if (Build.VERSION.SDK_INT > M) {
                    inputStream.bufferedReader().use(BufferedReader::readText)
                        .let(networkJson::decodeFromString)
                } else {
                    networkJson.decodeFromStream(inputStream)
                }
            }
        }

    companion object {
        private const val NEWS_ASSET = "news.json"
        private const val TOPICS_ASSET = "topics.json"
    }
}

private fun <T> List<T>.mapToChangeList(
    idGetter: (T) -> String,
) = mapIndexed { index, item ->
    NetworkChangeList(
        id = idGetter(item),
        changeListVersion = index,
        isDelete = false,
    )
}