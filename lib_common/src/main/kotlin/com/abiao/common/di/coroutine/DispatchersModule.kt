package com.abiao.common.di.coroutine

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatch(NiaDispatchers.IO)
    @Singleton
    fun providesIODispatch(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatch(NiaDispatchers.Default)
    @Singleton
    fun providesDefaultDispatch(): CoroutineDispatcher = Dispatchers.Default
}