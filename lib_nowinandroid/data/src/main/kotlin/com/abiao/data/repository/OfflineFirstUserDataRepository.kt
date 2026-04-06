package com.abiao.data.repository

import com.abiao.data.datastore.NiaUserPreferencesDataSource
import com.abiao.model.DarkThemeConfig
import com.abiao.model.ThemeBrand
import com.abiao.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: NiaUserPreferencesDataSource
): UserDataRepository{
    override val userData: Flow<UserData> =
        niaPreferencesDataSource.userData

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        niaPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        niaPreferencesDataSource.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        niaPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }
}