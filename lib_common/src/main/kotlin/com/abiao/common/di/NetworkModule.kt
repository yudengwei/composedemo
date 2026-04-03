package com.abiao.common.di

import com.abiao.common.net.ConnectivityManagerNetworkMonitor
import com.abiao.common.net.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface NetworkModule {

    @Binds
    fun bindNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}