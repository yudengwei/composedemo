package com.abiao.data.datastore

import androidx.datastore.core.DataStore
import com.abiao.lib_nowinandroid.datastore.UserPreferences
import com.abiao.lib_nowinandroid.datastore.copy
import com.abiao.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NiaUserPreferencesDataSource @Inject constructor(
    private val userDataStore: DataStore<UserPreferences>
) {
    val userData = userDataStore.data
        .map {
            UserData(
                shouldHideOnboarding = it.shouldHideOnboarding
            )
        }

    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        userDataStore.updateData {
            it.copy {
                this.shouldHideOnboarding = shouldHideOnboarding
            }
        }
    }
}