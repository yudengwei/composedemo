package com.abiao.lib_chatdemo.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.abiao.lib_chatdemo.R

internal val MulishFontFamily = FontFamily(
    Font(R.font.mulish_regular, FontWeight.Normal),
    Font(R.font.mulish_medium, FontWeight.Medium),
    Font(R.font.mulish_bold, FontWeight.Bold),
    Font(R.font.mulish_semibold, FontWeight.SemiBold),
    Font(R.font.mulish_italic, FontWeight.Normal, style = FontStyle.Italic),
)

internal val M3 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 10.sp,
    textAlign = TextAlign.Left,
    lineHeight = 16.sp,
    letterSpacing = 0.sp
)

internal val M2 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    textAlign = TextAlign.Left,
    lineHeight = 16.sp,
    letterSpacing = 0.sp
)

internal val M1 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    textAlign = TextAlign.Left,
    lineHeight = 20.sp,
    letterSpacing = 0.sp
)

internal val B2 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    textAlign = TextAlign.Left,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

internal val B1 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    textAlign = TextAlign.Left,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

internal val S2 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    textAlign = TextAlign.Left,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
)

internal val S1 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    textAlign = TextAlign.Left,
    lineHeight = 30.sp,
    letterSpacing = 0.sp
)

internal val H2 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    textAlign = TextAlign.Left,
)

internal val H1 = TextStyle(
    fontFamily = MulishFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp,
    textAlign = TextAlign.Left
)

internal val ChatTypography = Typography(
    displaySmall = M3,
    displayMedium = M2,
    displayLarge = M1,
    headlineSmall = B2,
    headlineMedium = B1,
    headlineLarge = S2,
    titleSmall = S1,
    titleMedium = H2,
    titleLarge = H1
)