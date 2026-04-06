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

package com.abiao.lib_nowinandroid.ui.feature.topic.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.abiao.lib_nowinandroid.navigation.Navigator
import com.abiao.lib_nowinandroid.ui.feature.topic.TopicScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.topicEntry(navigator: Navigator) {
    entry<TopicNavKey>(
        metadata = ListDetailSceneStrategy.detailPane(),
    ) { key ->
        val id = key.id
        TopicScreen()
    }
}
