package com.abiao.lib_widget.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun MyAlertDialog(

) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().wrapContentSize()
    ) {
        Button(
            onClick = {
                showDialog = true
            }
        ) {
            Text("show dialog")
        }
    }

    if (showDialog) {
        AlertDialog(
            icon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "")
            },
            title = {
                Text("我是标题")
            },
            text = {
                Text("我是内容")
            },
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("确认")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("取消")
                }
            }
        )
    }
}