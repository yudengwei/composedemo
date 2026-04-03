package com.abiao.network

import com.abiao.network.model.NetworkChangeList
import com.abiao.network.model.NetworkTopic

interface NiaNetworkDataSource {

    suspend fun getNetworkTopic(): List<NetworkTopic>

    suspend fun getNetworkChangeList(): List<NetworkChangeList>
}