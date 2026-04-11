package com.abiao.lib_chatdemo.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.abiao.lib_chatdemo.R
import com.abiao.lib_chatdemo.ui.B2
import com.abiao.lib_chatdemo.ui.H2
import com.abiao.lib_chatdemo.ui.S2
import com.abiao.lib_chatdemo.weight.ChatButton
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
object IndexPageKey: NavKey

@Composable
fun IndexPage(
    onStartMessageClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.welcome),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 135.dp)
        )
        Spacer(modifier = Modifier.height(42.dp))
        Text(
            modifier = Modifier.padding(horizontal = 48.dp),
            text = stringResource(R.string.index_welcome),
            textAlign = TextAlign.Center,
            style = H2.copy(color = MaterialTheme.colorScheme.onSurface),
        )
        Spacer(modifier = Modifier.height(126.dp))
        Text(
            text = stringResource(R.string.index_hint),
            style = B2.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.height(18.dp))
        ChatButton(
            text = stringResource(R.string.start_message),
            modifier = Modifier
                .padding(horizontal = 24.dp),
            onClick = onStartMessageClick,
        )
    }
}