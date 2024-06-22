package com.imagepick.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abiao.common.theme.blue
import com.abiao.common.theme.white
import com.abiao.common.theme.white80

@Composable
internal fun CircleCheckBox(
    modifier: Modifier,
    text: String,
    enabled: Boolean,
    checked: Boolean,
    onClick: () -> Unit
) {
    val circleColor = if (enabled) {
        com.abiao.common.theme.white
    } else {
        com.abiao.common.theme.white80
    }
    val fillColor = com.abiao.common.theme.blue
    val textColor = com.abiao.common.theme.white
    val checkBoxSize = 24.dp
    val textMeasure = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .selectable(
                selected = checked,
                onClick = onClick,
                enabled = true,
                role = Role.Checkbox,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(
                    bounded = false,
                    radius = checkBoxSize / 2
                )
            )
            .wrapContentSize(align = Alignment.Center)
            .requiredSize(size = checkBoxSize)
    ) {
        val checkboxSide = this.size.width
        val checkboxRadius = checkboxSide / 2f
        val strokeWidth = checkboxSide / 10f
        drawCircle(
            color = circleColor,
            radius = checkboxRadius - strokeWidth / 2f,
            style = Stroke(width = strokeWidth)
        )
        if (checked) {
            drawCircle(
                color = fillColor,
                radius = checkboxRadius - strokeWidth
            )
        }
        if (text.isNotBlank()) {
            val textLayoutResult = textMeasure.measure(
                text = text,
                style = TextStyle(
                    color = textColor,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = (checkboxSide - textLayoutResult.size.width) / 2,
                    y = (checkboxSide - textLayoutResult.size.height) / 2
                )
            )
        }
    }
}