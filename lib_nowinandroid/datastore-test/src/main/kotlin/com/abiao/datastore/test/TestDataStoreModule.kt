package com.abiao.datastore.test

import androidx.datastore.core.DataStore
import com.abiao.data.datastore.UserSerializer
import com.abiao.data.datastore.di.UserPreferencesModule
import com.abiao.lib_nowinandroid.datastore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UserPreferencesModule::class]
)
object TestDataStoreModule {

    @Provides
    @Singleton
    fun provideTestDataStore(
        serializer: UserSerializer,
    ): DataStore<UserPreferences> = InMemoryDataStore(serializer.defaultValue)
}