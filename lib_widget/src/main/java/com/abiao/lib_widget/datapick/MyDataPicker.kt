package com.abiao.lib_widget.datapick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MyDataPick(

) {
    var showDataPicker by remember { mutableStateOf(false) }
    val dataPickerState = rememberDatePickerState()
    val selectData = dataPickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(top = 20.dp)
    ) {
        OutlinedTextField(
            value = selectData,
            onValueChange = {},
            label = { Text("ODB") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    showDataPicker = !showDataPicker
                }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                }
            },
            modifier = Modifier.fillMaxWidth().height(64.dp)
        )

        if (showDataPicker) {
            Popup(
                onDismissRequest = {
                    showDataPicker = false
                },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = dataPickerState,
                        showModeToggle = true
                    )
                }
            }
        }
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}