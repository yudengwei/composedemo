/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.abiao.lib_nowinandroid.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.abiao.lib_nowinandroid.R
import com.abiao.lib_nowinandroid.ui.feature.bookmarks.navigation.BookmarksNavKey
import com.abiao.lib_nowinandroid.ui.feature.foryou.navigation.ForYouNavKey
import com.abiao.lib_nowinandroid.ui.feature.interests.navigation.InterestsNavKey
import com.abiao.lib_nowinandroid.ui.icon.NiaIcons

/**
 * Type for the top level navigation items in the application. Contains UI information about the
 * current route that is used in the top app bar and common navigation UI.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is
 * selected.
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is
 * not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 */
data class TopLevelNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
)

val FOR_YOU = TopLevelNavItem(
    selectedIcon = NiaIcons.Upcoming,
    unselectedIcon = NiaIcons.UpcomingBorder,
    iconTextId = R.string.feature_foryou_api_title,
    titleTextId = R.string.app_name,
)

val BOOKMARKS = TopLevelNavItem(
    selectedIcon = NiaIcons.Bookmarks,
    unselectedIcon = NiaIcons.BookmarksBorder,
    iconTextId = R.string.feature_bookmarks_api_title,
    titleTextId = R.string.feature_bookmarks_api_title,
)

val INTERESTS = TopLevelNavItem(
    selectedIcon = NiaIcons.Grid3x3,
    unselectedIcon = NiaIcons.Grid3x3,
    iconTextId = R.string.feature_search_api_interests,
    titleTextId = R.string.feature_search_api_interests,
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    ForYouNavKey to FOR_YOU,
    BookmarksNavKey to BOOKMARKS,
    InterestsNavKey(null) to INTERESTS,
)
