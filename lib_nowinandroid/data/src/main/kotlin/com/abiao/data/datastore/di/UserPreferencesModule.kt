package com.abiao.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.abiao.common.di.coroutine.ApplicationScope
import com.abiao.lib_nowinandroid.datastore.UserPreferences
import com.abiao.data.datastore.UserSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserPreferencesModule {

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope,
        userSerializer: UserSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            scope = scope,
            serializer = userSerializer
        ) {
            context.dataStoreFile("user_preferences.pb")
        }

}