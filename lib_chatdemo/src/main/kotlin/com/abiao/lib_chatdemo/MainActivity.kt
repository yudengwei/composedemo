package com.abiao.lib_chatdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.abiao.lib_chatdemo.page.EnterCodePage
import com.abiao.lib_chatdemo.page.EnterCodePageKey
import com.abiao.lib_chatdemo.page.IndexPage
import com.abiao.lib_chatdemo.page.IndexPageKey
import com.abiao.lib_chatdemo.page.InputNumberPage
import com.abiao.lib_chatdemo.page.InputNumberPageKey
import com.abiao.lib_chatdemo.page.LoginPage
import com.abiao.lib_chatdemo.page.LoginPageKey
import com.abiao.lib_chatdemo.page.MainPageKey
import com.abiao.lib_chatdemo.page.MianPage
import com.abiao.lib_chatdemo.ui.ChatDemoTheme

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatDemoTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    val stack = rememberNavBackStack(MainPageKey)
                    val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
//                        entry<IndexPageKey> {
//                            IndexPage(
//                                onStartMessageClick = {
//                                    stack.add(InputNumberPageKey)
//                                }
//                            )
//                        }
//                        entry<InputNumberPageKey> {
//                            InputNumberPage(
//                                onBackClick = {
//                                    stack.removeAt(stack.size - 1)
//                                },
//                                onContinueClick = {
//                                    stack.add(EnterCodePageKey)
//                                }
//                            )
//                        }
//                        entry<EnterCodePageKey> {
//                            EnterCodePage(
//                                onBackClick = {
//                                    stack.removeAt(stack.size - 1)
//                                },
//                                onSuccess = {
//                                    stack.add(LoginPageKey)
//                                }
//                            )
//                        }
//                        entry<LoginPageKey> {
//                            LoginPage {
//                                stack.removeAt(stack.size - 1)
//                            }
//                        }
                        entry<MainPageKey> {
                            MianPage()
                        }
                    }

                    NavDisplay(
                        backStack = stack,
                        entryProvider = entryProvider,
                        onBack = {
                            stack.removeAt(stack.size - 1)
                        },
                        transitionSpec = {
                            (slideInHorizontally(
                                animationSpec = tween(300),
                                initialOffsetX = { fullWidth ->
                                    fullWidth
                                }
                            ) + fadeIn(animationSpec = tween(300))) togetherWith
                                    (slideOutHorizontally(
                                        animationSpec = tween(300),
                                        targetOffsetX = { fullWidth -> -fullWidth }
                                    ) + fadeOut(animationSpec = tween(300)))
                        },
                        popTransitionSpec = {
                            (slideInHorizontally(
                                animationSpec = tween(300),
                                initialOffsetX = {fullWidth ->
                                    -fullWidth
                                }
                            ) + fadeIn(animationSpec = tween(300))) togetherWith
                                    ((slideOutHorizontally(
                                        animationSpec = tween(300),
                                        targetOffsetX = { fullWidth -> fullWidth }
                                    ) + fadeOut(animationSpec = tween(300))))
                        }
                    )
                }
            }
        }
    }
}