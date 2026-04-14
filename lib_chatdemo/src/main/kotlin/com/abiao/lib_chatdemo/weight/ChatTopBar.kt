package com.abiao.lib_chatdemo.weight

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.model.TopEntry
import com.abiao.lib_chatdemo.ui.S1

@Composable
fun ChatTopBar(
    topEntry: TopEntry,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 16.dp)
    ) {
        if (topEntry.showBack) {
            TopBarIconButton(
                icon = R.drawable.back,
                onClick = { topEntry.onClickBack?.invoke() },
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        if (!topEntry.title.isNullOrEmpty()) {
            Text(
                text = topEntry.title,
                style = S1.copy(color = MaterialTheme.colorScheme.onSurface)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        topEntry.icons?.takeIf { it.isNotEmpty() }?.let { icons ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icons.forEachIndexed { index, icon ->
                    if (index > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    TopBarIconButton(
                        icon = icon,
                        onClick = { topEntry.onClickIcon?.invoke(index) },
                    )
                }
            }
        }
    }
}

@Composable
fun ChatTopBar(
    title: String? = null,
    @DrawableRes icons: List<Int>? = null,
    onClickIcon: ((Int) -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
) {
    ChatTopBar(
        topEntry = TopEntry(
            title = title,
            icons = icons,
            showBack = onBackClick != null,
            onClickBack = onBackClick,
            onClickIcon = onClickIcon,
        )
    )
}

@Composable
private fun TopBarIconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}
