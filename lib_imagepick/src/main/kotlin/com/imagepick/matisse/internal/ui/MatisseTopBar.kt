package com.imagepick.matisse.internal.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imagepick.R
import com.imagepick.matisse.ImageEngine
import com.imagepick.matisse.internal.logic.MatisseMediaBucketInfo

@Composable
internal fun MatisseTopBar(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = modifier
            .shadow(elevation = 4.dp)
            .background(color = colorResource(id = R.color.matisse_status_bar_color))
            .windowInsetsPadding(insets = WindowInsets.statusBarsIgnoringVisibility)
            .fillMaxWidth()
            .height(height = 56.dp)
            .background(color = colorResource(id = R.color.matisse_top_bar_background_color)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var menuExpanded by remember {
            mutableStateOf(false)
        }
        val coroutineScope = rememberCoroutineScope()
        Row(
            modifier = Modifier
                .padding(end = 30.dp)
                .clickableNoRipple {
                    menuExpanded = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val localActivity = LocalActivity.current
            Icon(
                modifier = Modifier
                    .clickableNoRipple {
                        localActivity?.finish()
                    }
                    .padding(start = 18.dp, end = 12.dp)
                    .fillMaxHeight()
                    .size(size = 24.dp),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = colorResource(id = R.color.matisse_top_bar_icon_color),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(weight = 1f, fill = false),
                text = title,
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.matisse_top_bar_text_color)
            )
            Icon(
                modifier = Modifier
                    .size(size = 32.dp),
                imageVector = Icons.Filled.KeyboardArrowDown,
                tint = colorResource(R.color.matisse_top_bar_icon_color),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun BucketDropdownMenu(
    modifier: Modifier,
    expand: Boolean,
    onDismissRequest: () -> Unit,
    mediaBuckets: List<MatisseMediaBucketInfo>,
    imageEngine: ImageEngine,
    onClickBucket: (MatisseMediaBucketInfo) -> Unit,
) {
    DropdownMenu(
        modifier = modifier
            .background(color = colorResource(id = R.color.matisse_dropdown_menu_background_color))
            .widthIn(min = 200.dp)
            .heightIn(max = 400.dp),
        expanded = expand,
        offset = DpOffset(x = 10.dp, y = (-10).dp),
        onDismissRequest = onDismissRequest
    ) {

    }
}