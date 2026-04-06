package com.abiao.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.abiao.lib_nowinandroid.datastore.DarkThemeConfigProto
import com.abiao.lib_nowinandroid.datastore.ThemeBrandProto
import com.abiao.lib_nowinandroid.datastore.UserPreferences
import com.abiao.lib_nowinandroid.datastore.copy
import com.abiao.model.ChangeListVersion
import com.abiao.model.DarkThemeConfig
import com.abiao.model.ThemeBrand
import com.abiao.model.UserData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.copy

class NiaUserPreferencesDataSource @Inject constructor(
    private val userDataStore: DataStore<UserPreferences>
) {
    val userData = userDataStore.data
        .map {
            UserData(
                shouldHideOnboarding = it.shouldHideOnboarding,
                themeBrand = when (it.themeBrand) {
                    null,
                    ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                    ThemeBrandProto.UNRECOGNIZED,
                    ThemeBrandProto.THEME_BRAND_DEFAULT,
                        -> ThemeBrand.DEFAULT
                    ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
                },
                darkThemeConfig = when (it.darkThemeConfig) {
                    null,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                        ->
                        DarkThemeConfig.FOLLOW_SYSTEM
                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                        DarkThemeConfig.LIGHT
                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                },
                useDynamicColor = it.useDynamicColor,
            )
        }

    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        userDataStore.updateData {
            it.copy {
                this.shouldHideOnboarding = shouldHideOnboarding
            }
        }
    }

    suspend fun getChangeListVersions() = userDataStore.data
        .map {
            ChangeListVersion(
                topicVersion = it.topicChangeListVersion
            )
        }
        .firstOrNull() ?: ChangeListVersion()

    suspend fun updateChangeListVersion(update: ChangeListVersion.() -> ChangeListVersion) {
        try {
            userDataStore.updateData { current ->
                val updateData = update(
                    ChangeListVersion(
                        current.topicChangeListVersion
                    )
                )
                current.copy {
                    topicChangeListVersion = updateData.topicVersion
                }
            }
        } catch (e: Exception) {
            Log.e("NiaPreferences", "Failed to update user preferences", e)

        }
    }

    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        userDataStore.updateData {
            it.copy {
                this.themeBrand = when (themeBrand) {
                    ThemeBrand.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
                    ThemeBrand.ANDROID -> ThemeBrandProto.THEME_BRAND_ANDROID
                }
            }
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userDataStore.updateData {
            it.copy { this.useDynamicColor = useDynamicColor }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userDataStore.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }
}