package com.abiao.network

import com.abiao.network.model.NetworkChangeList
import com.abiao.network.model.NetworkTopic

interface NiaNetworkDataSource {

    suspend fun getNetworkTopic(ids: List<String>? = null): List<NetworkTopic>

    suspend fun getNetworkChangeList(after: Int? = null): List<NetworkChangeList>
}