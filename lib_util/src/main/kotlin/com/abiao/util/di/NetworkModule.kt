package com.abiao.util.di

import com.abiao.util.net.ConnectivityManagerNetworkMonitor
import com.abiao.util.net.NetworkMonitor
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