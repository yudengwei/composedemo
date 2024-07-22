package com.abiao.crane.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abiao.crane.R
import com.abiao.crane.data.CalendarState
import com.abiao.crane.viewmodel.MainViewModel

@Composable
fun CalendarScreen(
    mainViewModel: MainViewModel,
    onBackPressed: () -> Unit,
) {
    val calendarState = remember {
        mainViewModel.calendarState
    }
    CalendarContent(
        calendarState = calendarState,
        onBackPressed = onBackPressed
    )
}

@Composable
fun CalendarContent(
    calendarState: CalendarState,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
        topBar = {
            CalendarTopAppBar(
                calendarState = calendarState,
                onBackPressed = onBackPressed
            )
        }
    ) {contentPadding ->
        Calendar(
            contentPadding = contentPadding
        )
    }
}

@Composable
fun Calendar(
    contentPadding: PaddingValues
) {

}

@Composable
fun CalendarTopAppBar(
    calendarState: CalendarState,
    onBackPressed: () -> Unit
) {
    val calendarUiState = calendarState.calendarUiState.value
    Column{
        Spacer(modifier = Modifier
            .windowInsetsTopHeight(WindowInsets.statusBars)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primaryVariant)
        )
        TopAppBar(
            title = {
                Text(
                    text = if (calendarUiState.hasSelectedDates) {
                        calendarUiState.selectedDatesFormatted
                    } else {
                        stringResource(id = R.string.calendar_select_dates_title)
                    }
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
                }
            },
            backgroundColor = MaterialTheme.colors.primaryVariant,
            elevation = 0.dp
        )
    }
}