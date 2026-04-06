package com.abiao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abiao.database.dao.TopicDao
import com.abiao.database.model.TopicEntity

@Database(
    entities = [TopicEntity::class],
    version = 1,
    exportSchema = true
)
internal abstract class NiaDatabase: RoomDatabase() {
    abstract fun topicDao(): TopicDao
}