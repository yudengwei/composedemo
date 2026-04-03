package com.abiao.data.repository

import com.abiao.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicsRepository {

    fun getTopics(): Flow<List<Topic>>

    fun getTopicById(ids: List<String>): Flow<Topic>
}