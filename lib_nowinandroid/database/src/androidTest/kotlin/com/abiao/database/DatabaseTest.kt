package com.abiao.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.abiao.database.dao.TopicDao
import org.junit.After
import org.junit.Before

internal abstract class DatabaseTest {

    private lateinit var db: NiaDatabase
    protected lateinit var topicDao: TopicDao

    @Before
    fun setup() {
        db = run {
            val context = ApplicationProvider.getApplicationContext<Context>()
            Room.inMemoryDatabaseBuilder(
                context,
                NiaDatabase::class.java
            ).build()
        }
        topicDao = db.topicDao()
    }

    @After
    fun teardown() = db.close()
}