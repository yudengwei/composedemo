package com.imagepick.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abiao.common.theme.blue

@Composable
internal fun LoadingDialog(visible : Boolean) {
    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickNoRipple {
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(42.dp),
                color = com.abiao.common.theme.blue
            )
        }
    }
}