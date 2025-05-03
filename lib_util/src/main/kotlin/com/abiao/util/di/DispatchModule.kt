package com.abiao.util.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: ABiaoDispatcher)

enum class ABiaoDispatcher {
    Default,
    IO,
}

@Module
@InstallIn(SingletonComponent::class)
object DispatchModule {

    @Provides
    @Dispatcher(ABiaoDispatcher.IO)
    fun providesIODispatcher() = Dispatchers.IO

    @Provides
    @Dispatcher(ABiaoDispatcher.Default)
    fun providesDefaultDispatcher() = Dispatchers.Default
}