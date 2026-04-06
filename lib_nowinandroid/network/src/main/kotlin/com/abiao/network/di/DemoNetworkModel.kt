package com.abiao.network.di

import com.abiao.network.NiaNetworkDataSource
import com.abiao.network.demo.DemoNiaNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DemoNetworkModel {

    @Binds
    fun bindsDemoNetworkDataSource(
        imp: DemoNiaNetworkDataSource
    ): NiaNetworkDataSource
}