package com.abiao.lib_chatdemo.weight

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.ui.S1

@Composable
fun ChatTopBar(
    title: String? = null,
    showBackIcon: Boolean = true,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 16.dp)
    ) {
        if (showBackIcon) {
            Icon(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onBackClick?.invoke()
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        if (!title.isNullOrEmpty()) {
            Text(
                text = title,
                style = S1.copy(color = MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}