package com.abiao.data.repository

import com.abiao.data.Synchronizer
import com.abiao.data.changeListSync
import com.abiao.data.model.asEntity
import com.abiao.database.dao.TopicDao
import com.abiao.database.model.TopicEntity
import com.abiao.database.model.asExternalModel
import com.abiao.model.ChangeListVersion
import com.abiao.model.Topic
import com.abiao.network.NiaNetworkDataSource
import com.abiao.network.model.NetworkTopic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineTopicRepository @Inject constructor(
    private val topicDao: TopicDao,
    private val niaNetworkDataSource: NiaNetworkDataSource
): TopicsRepository {

    override fun getTopics(): Flow<List<Topic>> =
        topicDao.getAllTopic().map {
            it.map(TopicEntity::asExternalModel)
        }

    override fun getTopicById(id: String): Flow<Topic> =
        topicDao.getTopicById(id).map { it.asExternalModel() }

    override suspend fun syncWith(synchronizer: Synchronizer) =
        synchronizer.changeListSync(
            versionReader = ChangeListVersion::topicVersion,
            changeListFetcher = { currentVersion ->
                niaNetworkDataSource.getNetworkChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(topicVersion = latestVersion)
            },
            modelDeleter = topicDao::delete,
            modelUpdater = { changedIds ->
                val networkTopics = niaNetworkDataSource.getNetworkTopic(ids = changedIds)
                topicDao.upsert(
                    topicEntity = networkTopics.map(NetworkTopic::asEntity),
                )
            },
        )
}