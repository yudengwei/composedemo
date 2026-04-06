/*
 * Copyright 2023 The Android Open Source Project
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

package com.abiao.lib_nowinandroid.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * A class to model background color and tonal elevation values for Now in Android.
 */
@Immutable
data class TintTheme(
    val iconTint: Color = Color.Unspecified,
)

/**
 * A composition local for [TintTheme].
 *  1. 定义一个 LocalXXX
 *   2. 在上层用 CompositionLocalProvider 提供值
 *   3. 在下层通过 LocalXXX.current 读取
 *     staticCompositionLocalOf 和 compositionLocalOf 的主要区别是重组策略：
 *
 *   - staticCompositionLocalOf
 *     读取不做精细追踪。
 *     如果提供的值变了，CompositionLocalProvider 下面整块内容会重组。
 *     适合“基本不变”或者“很少变”的值，比如主题对象、间距系统、图标风格。
 *   - compositionLocalOf
 *     会追踪哪些 Composable 读了这个值。
 *     值变化时，只重组真正读取它的地方。
 *     适合“可能经常变化”的值。
 *
 *   所以经验上可以这样记：
 *
 *   - 很少变化的全局 UI 配置，用 staticCompositionLocalOf
 *   - 经常变化、希望更细粒度刷新的值，用 compositionLocalOf
 *
 */
val LocalTintTheme = staticCompositionLocalOf { TintTheme() }
