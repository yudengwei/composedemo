package com.imagepick.ui.compose

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imagepick.imagengine.ImageEngine
import com.abiao.common.theme.blue
import com.abiao.common.theme.white
import com.imagepick.viewmodel.UiModelStatus

@Composable
internal fun ImagePickTopBar(
    uiModelStatus: UiModelStatus,
    imageEngine : ImageEngine
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = com.abiao.common.theme.blue)
            .statusBarsPadding()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var menuExpanded by remember {
            mutableStateOf(false)
        }
        Row {
            val context = LocalContext.current
            Icon(
                modifier = Modifier
                    .clickNoRipple {
                        (context as Activity).finish()
                    }
                    .padding(start = 20.dp, end = 10.dp)
                    .fillMaxHeight(),
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                tint = com.abiao.common.theme.white,
                contentDescription = null)
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickNoRipple {
                        menuExpanded = true
                    },
                text = uiModelStatus.title,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = com.abiao.common.theme.white
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(32.dp),
                tint = com.abiao.common.theme.white,
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null)
        }
        BucketDropdownMenu(
            uiModelStatus = uiModelStatus,
            menuExpanded = menuExpanded,
            imageEngine = imageEngine,
            onDismissRequest = {
                menuExpanded = false
            }
        )
    }
}

@Composable
internal fun BucketDropdownMenu(
    uiModelStatus: UiModelStatus,
    menuExpanded : Boolean,
    imageEngine: ImageEngine,
    onDismissRequest : () -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .widthIn(min = 200.dp)
            .heightIn(max = 400.dp)
            .background(color = com.abiao.common.theme.white),
        offset = DpOffset(x = 10.dp, y = (-10).dp),
        expanded = menuExpanded,
        onDismissRequest = onDismissRequest
    ) {
        for (bucket in uiModelStatus.mediaBuckets) {
            DropdownMenuItem(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(shape = RoundedCornerShape(5.dp))
                        ) {
                            bucket.mediaSource.firstOrNull()?.let {mediaSource ->
                                imageEngine.Thumbnail(mediaResource = mediaSource)
                            }
                        }
                        Text(
                            modifier = Modifier
                                .padding(10.dp),
                            text = bucket.name
                        )
                        Text(
                            text = "(${bucket.mediaSource.size})"
                        )
                    }
                },
                onClick = {
                    onDismissRequest()
                })
        }
    }
}