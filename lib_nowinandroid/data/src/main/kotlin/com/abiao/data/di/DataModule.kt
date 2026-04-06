package com.abiao.data.di

import com.abiao.data.repository.OfflineFirstUserDataRepository
import com.abiao.data.repository.OfflineTopicRepository
import com.abiao.data.repository.TopicsRepository
import com.abiao.data.repository.UserDataRepository
import com.abiao.data.uitl.TimeZoneBroadcastMonitor
import com.abiao.data.uitl.TimeZoneMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsTopicRepository(
        topicsRepository: OfflineTopicRepository,
    ): TopicsRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    internal abstract fun binds(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor
}