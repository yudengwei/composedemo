package com.abiao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.abiao.database.model.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    @Query("select * from topics")
    fun getAllTopic(): Flow<List<TopicEntity>>

    @Query("select * from topics where id = :id")
    fun getTopicById(id: String): Flow<TopicEntity>

    @Query("select * from topics where id in (:ids)")
    fun getTopicsFromIds(ids: Set<String>): Flow<List<TopicEntity>>

    @Query("select * from topics")
    suspend fun getTopicByIdOneWay(): List<TopicEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreTopicEntity(topicEntity: List<TopicEntity>)

    @Upsert
    suspend fun upsert(topicEntity: List<TopicEntity>)

    @Query("delete from topics where id in (:ids)")
    suspend fun delete(ids: List<String>)
}