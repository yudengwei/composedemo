package com.abiao.lib_nowinandroid.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation3.runtime.NavKey
import com.abiao.common.net.NetworkMonitor
import com.abiao.data.uitl.TimeZoneMonitor
import com.abiao.lib_nowinandroid.navigation.NavigationState
import com.abiao.lib_nowinandroid.navigation.TOP_LEVEL_NAV_ITEMS
import com.abiao.lib_nowinandroid.navigation.rememberNavigationState
import com.abiao.lib_nowinandroid.ui.feature.foryou.navigation.ForYouNavKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone

@Composable
fun rememberNiaAppState(
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): NiaAppState {
    val navigationState = rememberNavigationState(ForYouNavKey, TOP_LEVEL_NAV_ITEMS.keys)

    return remember(
        navigationState,
        coroutineScope,
        networkMonitor,
        timeZoneMonitor,
    ) {
        NiaAppState(
            navigationState = navigationState,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
            timeZoneMonitor = timeZoneMonitor,
        )
    }
}

/**
 * 增加性能，让compose能智能跳过重组，如果不加则每次都会重组
 */
@Stable
class NiaAppState(
    val navigationState: NavigationState,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
) {
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val currentTimeZone = timeZoneMonitor.currentTimeZone
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(5_000),
            TimeZone.currentSystemDefault(),
        )
}