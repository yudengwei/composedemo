package com.abiao.lib_widget.check

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyCheckBox(

) {
    var checked by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
            }
        )
        Text(
            text = if (checked) "click cnacel" else "click sure",
            modifier = Modifier.clickable {
                checked = !checked
            }
        )
    }
}

@Composable
fun CheckAll(

) {
    val childStatus = remember { mutableStateListOf(false, false, false) }

    val parentState = when {
        childStatus.all { it } -> ToggleableState.On
        childStatus.all { !it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column {
        if (childStatus.all { it }) {
            Text("all option selected")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("select all")
            TriStateCheckbox(
                state = parentState,
                onClick = {
                    val newState = parentState != ToggleableState.On
                    childStatus.forEachIndexed { index, b ->
                        childStatus[index] = newState
                    }
                }
            )
        }

        childStatus.forEachIndexed { index, b ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("option${index}")
                Checkbox(
                    checked = b,
                    onCheckedChange = {
                        childStatus[index] = it
                    }
                )
            }
        }
    }
}