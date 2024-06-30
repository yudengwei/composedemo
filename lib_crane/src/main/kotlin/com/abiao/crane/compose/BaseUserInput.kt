package com.abiao.crane.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abiao.crane.ui.captionTextStyle

@Composable
fun CraneUserInput(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    tint: Color = LocalContentColor.current
) {
    CraneBaseUserInput(
        modifier = modifier,
        onClick = onClick,
        caption = caption,
        vectorImageId = vectorImageId,
        tintIcon = { text.isNotEmpty() },
        tint = tint
    ) {
        Text(text = text, style = MaterialTheme.typography.body1.copy(color = tint))
    }
}

@Composable
fun CraneBaseUserInput(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @DrawableRes vectorImageId: Int? = null,
    tintIcon: () -> Boolean,
    tint: Color = LocalContentColor.current,
    caption: String? = null,
    showCaption: () -> Boolean = { true },
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        color = MaterialTheme.colors.primaryVariant
    ) {
        Row(
            modifier = Modifier
                .padding(all = 12.dp)
        ) {
            if (vectorImageId != null) {
                Icon(
                    modifier = Modifier.size(width = 24.dp, height = 24.dp),
                    painter = painterResource(id = vectorImageId), 
                    contentDescription = null,
                    tint = if (tintIcon()) tint else Color(0x80FFFFFF) 
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            if (caption != null && showCaption()) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = caption,
                    style = captionTextStyle.copy(tint)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                content()
            }
        }
    }
}