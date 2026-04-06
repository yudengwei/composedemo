package com.abiao.data.repository

import com.abiao.model.DarkThemeConfig
import com.abiao.model.ThemeBrand
import com.abiao.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)

    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    /**
     * Sets the desired dark theme config.
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets the preferred dynamic color config.
     */
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}