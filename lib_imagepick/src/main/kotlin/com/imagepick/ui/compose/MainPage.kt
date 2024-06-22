package com.imagepick.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imagepick.R
import com.imagepick.viewmodel.MainUiModel

@Composable
fun MainPage(
    mainUiModel: MainUiModel,
    imageAndVideo: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .statusBarsPadding()
                    .height(60.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 22.dp),
                    text = stringResource(id = R.string.app_name),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding(), start = 20.dp, end = 20.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 20.dp),
                text = "maxSelectable",
                style = MaterialTheme.typography.titleLarge
            )
            Row {
                for (i in 1 until 4) {
                    RadioButton(tips = "$i", selected = mainUiModel.maxSelect == i) {
                        mainUiModel.onMaxSelectClick(i)
                    }
                }
            }
            Button(
                tips = "图片+视频",
                enable = true,
                onClick = imageAndVideo
            )
        }
    }
}

@Composable
private fun RadioButton(
    tips: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = tips,
            style = MaterialTheme.typography.labelLarge
        )
        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}

@Composable
private fun Button(
    tips: String,
    enable: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enable,
        onClick = onClick
    ) {
        Text(
            text = tips,
            style = MaterialTheme.typography.titleSmall
        )
    }
}