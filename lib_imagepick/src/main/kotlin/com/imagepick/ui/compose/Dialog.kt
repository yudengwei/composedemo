package com.imagepick.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.abiao.common.theme.white

@Composable
fun MyAlertDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (visible) {
        AlertDialog(
            title = {
                Text(text = "我是标题")
            },
            text = {
                Text(text = "我是内容")
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                Text(
                    modifier = Modifier
                        .clickable {
                            onConfirm()
                        },
                    text = "确认"
                )
            })
    }
}

@Composable
fun MyDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
) {
    if (visible) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .background(color = com.abiao.common.theme.white, shape = RoundedCornerShape(10.dp))
                    .size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "我是dialog弹窗")
            }
        }
    }

}