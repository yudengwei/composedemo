package com.abiao.data.repository

import com.abiao.data.Syncable
import com.abiao.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicsRepository: Syncable {

    fun getTopics(): Flow<List<Topic>>

    fun getTopicById(id: String): Flow<Topic>
}