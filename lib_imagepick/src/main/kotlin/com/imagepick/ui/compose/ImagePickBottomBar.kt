package com.imagepick.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abiao.common.theme.black
import com.abiao.common.theme.black80
import com.abiao.common.theme.blue
import com.abiao.common.theme.blue80
import com.abiao.common.theme.white
import com.imagepick.viewmodel.ImagePickViewModel

@Composable
internal fun ImagePickBottomBar(
    imagePickViewModel: ImagePickViewModel
) {
    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(52.dp)
            .background(com.abiao.common.theme.white),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp),
            text = "预览",
            color = if (imagePickViewModel.selectedResources.isEmpty()) com.abiao.common.theme.black80 else com.abiao.common.theme.black,
            fontSize = 15.sp
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp),
            text = "确认(${imagePickViewModel.selectedResources.size}/${imagePickViewModel.maxSelectable})",
            color = if (imagePickViewModel.selectedResources.isEmpty()) com.abiao.common.theme.blue80 else com.abiao.common.theme.blue,
            fontSize = 15.sp
        )
    }
}