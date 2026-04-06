package com.abiao.database.di

import com.abiao.database.NiaDatabase
import com.abiao.database.dao.TopicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    @Singleton
    fun provideTopicDao(
        niaDatabase: NiaDatabase
    ): TopicDao = niaDatabase.topicDao()
}