package com.abiao.lib_chatdemo.page

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.ui.B2
import kotlinx.serialization.Serializable

@Serializable
object MainPageKey: NavKey

@Composable
fun MianPage(

) {
    var selected by rememberSaveable { mutableStateOf(0) }
    Scaffold(
        bottomBar = {
            ChatBottomBar(
                selected = selected,
                onSelectedChange = {
                    selected = it
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Composable
private fun ChatBottomBar(
    selected: Int,
    onSelectedChange: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 16.dp,
        tonalElevation = 0.dp,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .navigationBarsPadding()
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

@Composable
private fun NavigationItem(
    isSelected: Boolean,
    title: String,
    @DrawableRes icon:  Int,
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
            ){
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
                        .background(color = MaterialTheme.colorScheme.onSurface, shape = CircleShape)
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
