package com.abiao.datastore.test

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.updateAndGet

class InMemoryDataStore<T>(initialValue: T): DataStore<T> {

    override val data = MutableStateFlow(initialValue)

    override suspend fun updateData(transform: suspend (t: T) -> T) =
        data.updateAndGet{ transform(it) }
}