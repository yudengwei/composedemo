package com.imagepick.matisse.internal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imagepick.R
import com.imagepick.matisse.internal.logic.MatisseMediaSelectState

@Composable
internal fun MatisseCheckbox(
    modifier: Modifier,
    selectState: MatisseMediaSelectState,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickableNoRipple(onClick),
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = CircleShape)
                .then(
                    other = if (selectState.isSelected) {
                        Modifier.background(color = colorResource(id = R.color.matisse_check_box_circle_fill_color))
                    } else {
                        Modifier.background(color = Color(0x1A000000))
                            .border(
                                width = 1.dp,
                                shape = CircleShape,
                                color = colorResource(
                                    id = if (selectState.isEnabled) {
                                        R.color.matisse_check_box_circle_color
                                    } else {
                                        R.color.matisse_check_box_circle_color_if_disable
                                    }
                                )
                            )
                    }
                )
        )
        val positionFormatted = selectState.positionFormatted
        if (!positionFormatted.isNullOrBlank()) {
            BasicText(
                modifier = Modifier
                    .matchParentSize()
                    .wrapContentSize(align = Alignment.Center),
                text = positionFormatted,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 6.sp,
                    maxFontSize = 18.sp,
                    stepSize = 1.sp
                ),
                style = TextStyle(
                    color = colorResource(id = R.color.matisse_check_box_text_color),
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}