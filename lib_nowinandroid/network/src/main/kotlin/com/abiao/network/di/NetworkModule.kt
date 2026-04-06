package com.abiao.network.di

import android.content.Context
import com.abiao.network.NiaNetworkDataSource
import com.abiao.network.demo.DemoAssetManager
import com.abiao.network.demo.DemoNiaNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesDemoAssetManager(
        @ApplicationContext context: Context
    ): DemoAssetManager = DemoAssetManager(context.assets::open)
}