package com.abiao.model

data class UserData(
    val shouldHideOnboarding: Boolean,
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
)
