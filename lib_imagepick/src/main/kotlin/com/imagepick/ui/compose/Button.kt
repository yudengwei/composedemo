package com.imagepick.ui.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.abiao.common.theme.black
import com.abiao.common.theme.blue
import com.abiao.common.theme.white

@Composable
fun MyButton(
    modifier: Modifier
) {
    val interaction = remember {
        MutableInteractionSource()
    }
    val (text, textColor, backgroundColor) = when {
        interaction.collectIsPressedAsState().value -> ButtonStatus("按压状态",
            com.abiao.common.theme.black,
            com.abiao.common.theme.white
        )
        else -> ButtonStatus("正常状态", com.abiao.common.theme.white, com.abiao.common.theme.blue)
    }
    Button(
        modifier = modifier,
        onClick = { },
        interactionSource = interaction,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 15.sp
            )
    }
}

data class ButtonStatus(val text : String, val textColor : Color, val backgroundColor: Color)