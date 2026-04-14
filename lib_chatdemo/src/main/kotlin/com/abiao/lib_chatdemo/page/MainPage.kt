package com.abiao.lib_chatdemo.page

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.model.Contact
import com.abiao.lib_chatdemo.model.TopEntry
import com.abiao.lib_chatdemo.ui.B2
import com.abiao.lib_chatdemo.weight.ChatTopBar
import kotlinx.serialization.Serializable

@Serializable
object MainPageKey : NavKey

@Composable
fun MianPage(
    topEntries: List<TopEntry>,
    contacts: List<Contact>
) {
    var selected by rememberSaveable { mutableStateOf(0) }
    var mSelectedTopEntry by remember { mutableStateOf(topEntries[selected]) }

    Scaffold(
        bottomBar = {
            ChatBottomBar(
                selected = selected,
                onSelectedChange = {
                    selected = it
                }
            )
        },
        topBar = {
            ChatTopBar(
                topEntry = mSelectedTopEntry
            )
        },
        modifier = Modifier
            .statusBarsPadding()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            val stack = rememberNavBackStack(ContactPageKey)
            val entryProvide: (NavKey) -> NavEntry<NavKey> = entryProvider {
                entry<ContactPageKey> {
                    ContactPage(
                        contacts = contacts
                    )
                }
            }
            NavDisplay(
                backStack = stack,
                entryProvider = entryProvide,
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

@Composable
private fun ChatBottomBar(
    selected: Int,
    onSelectedChange: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        TopDropShadow(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-12).dp)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                NavigationItem(
                    isSelected = selected == 0,
                    onSelectedChange = onSelectedChange,
                    position = 0,
                    icon = R.drawable.ic_contact,
                    title = stringResource(R.string.contact)
                )
                NavigationItem(
                    isSelected = selected == 1,
                    onSelectedChange = onSelectedChange,
                    position = 1,
                    icon = R.drawable.ic_chat,
                    title = stringResource(R.string.chat)
                )
                NavigationItem(
                    isSelected = selected == 2,
                    onSelectedChange = onSelectedChange,
                    position = 2,
                    icon = R.drawable.ic_more,
                    title = stringResource(R.string.more)
                )
            }
        }
    }
}

@Composable
private fun TopDropShadow(
    modifier: Modifier = Modifier,
    height: Dp = 12.dp,
    color: Color = Color.Black.copy(alpha = 0.08f),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        color,
                    )
                )
            )
    )
}

@Composable
private fun NavigationItem(
    isSelected: Boolean,
    title: String,
    @DrawableRes icon: Int,
    onSelectedChange: (Int) -> Unit,
    position: Int
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(60.dp, 45.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onSelectedChange(position)
            },
        contentAlignment = Alignment.Center,
    ) {
        if (isSelected) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = B2.copy(color = MaterialTheme.colorScheme.onSurface)
                )
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = CircleShape
                        )
                )
            }

        } else {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}
