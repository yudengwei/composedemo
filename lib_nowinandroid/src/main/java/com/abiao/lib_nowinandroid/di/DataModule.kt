package com.abiao.lib_nowinandroid.di

import com.abiao.lib_nowinandroid.repository.OfflineFirstUserDataRepository
import com.abiao.lib_nowinandroid.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository
    ): UserDataRepository
}