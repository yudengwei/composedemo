package com.abiao.lib_nowinandroid.source

import androidx.datastore.core.DataStore
import com.abiao.lib_nowinandroid.data.DarkThemeConfigProto
import com.abiao.lib_nowinandroid.data.ThemeBrandProto
import com.abiao.lib_nowinandroid.data.UserPreferences
import com.abiao.lib_nowinandroid.data.model.DarkThemeConfig
import com.abiao.lib_nowinandroid.data.model.ThemeBrand
import com.abiao.lib_nowinandroid.data.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NiaPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    val userData = userPreferences.data
        .map {
            UserData(
                darkThemeConfig = when (it.darkThemeConfig) {
                    null,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM, -> DarkThemeConfig.DARK
                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                },
                useDynamicColor = it.useDynamicColor,
                themeBrand = when (it.themeBrand) {
                    null,
                    ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                    ThemeBrandProto.UNRECOGNIZED,
                    ThemeBrandProto.THEME_BRAND_DEFAULT,
                        -> ThemeBrand.DEFAULT
                    ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
                }
            )
        }
}