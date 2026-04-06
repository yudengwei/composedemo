package com.abiao.database

import com.abiao.database.model.TopicEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class TopicDaoTest : DatabaseTest() {

    @Test
    fun getAllTopicTest() = runTest {
        insertTopics()
        val saveTargets = topicDao.getAllTopic().first()
        assertEquals(
            listOf("1", "2", "3"),
            saveTargets.map { it.id },
        )
    }

    @Test
    fun getTopic() = runTest {
        insertTopics()

        val savedTopicEntity = topicDao.getTopicById("2").first()

        kotlin.test.assertEquals("performance", savedTopicEntity.name)
    }

    @Test
    fun getTopics_oneOff() = runTest {
        insertTopics()

        val savedTopics = topicDao.getTopicByIdOneWay()

        kotlin.test.assertEquals(
            listOf("1", "2", "3"),
            savedTopics.map { it.id },
        )
    }

    @Test
    fun getTopics_byId() = runTest {
        insertTopics()

        val savedTopics = topicDao.getTopicsFromIds(setOf("1", "2"))
            .first()

        kotlin.test.assertEquals(listOf("compose", "performance"), savedTopics.map { it.name })
    }

    @Test
    fun insertTopic_newEntryIsIgnoredIfAlreadyExists() = runTest {
        insertTopics()
        topicDao.insertOrIgnoreTopicEntity(
            listOf(
                testTopicEntity(
                    "1",
                    "compose"
                )
            ),
        )

        val savedTopics = topicDao.getTopicByIdOneWay()

        kotlin.test.assertEquals(3, savedTopics.size)
    }

    @Test
    fun upsertTopic_existingEntryIsUpdated() = runTest {
        insertTopics()
        topicDao.upsert(
            listOf(
                testTopicEntity(
                    "1",
                    "newName"
                )
            ),
        )

        val savedTopics = topicDao.getTopicByIdOneWay()

        kotlin.test.assertEquals(3, savedTopics.size)
        kotlin.test.assertEquals("newName", savedTopics.first().name)
    }

    @Test
    fun deleteTopics_byId_existingEntriesAreDeleted() = runTest {
        insertTopics()
        topicDao.delete(listOf("1", "2"))

        val savedTopics = topicDao.getTopicByIdOneWay()

        kotlin.test.assertEquals(1, savedTopics.size)
        kotlin.test.assertEquals("3", savedTopics.first().id)
    }

    private suspend fun insertTopics() {
        val topicEntities = listOf(
            testTopicEntity("1", "compose"),
            testTopicEntity(
                "2",
                "performance"
            ),
            testTopicEntity("3", "headline"),
        )
        topicDao.insertOrIgnoreTopicEntity(topicEntities)
    }
}

private fun testTopicEntity(
    id: String = "0",
    name: String,
) = TopicEntity(
    id = id,
    name = name,
    shortDescription = "",
    longDescription = "",
    url = "",
    imageUrl = "",
)