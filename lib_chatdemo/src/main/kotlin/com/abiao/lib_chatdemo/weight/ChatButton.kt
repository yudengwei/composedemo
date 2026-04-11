package com.abiao.lib_chatdemo.weight

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.ui.S2
import com.abiao.util.log.Logger

@Composable
fun ChatButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enable: Boolean = true
) {
    SideEffect {
        Logger.d("ydw: enable: $enable")
    }
    Button(
        modifier = modifier
            .height(52.dp),
        shape = RoundedCornerShape(20.dp),
        enabled = enable,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
        )
    ) {
        Text(
            text = text,
            style = S2
        )
    }
}