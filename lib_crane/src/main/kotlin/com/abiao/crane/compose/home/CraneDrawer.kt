package com.abiao.crane.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abiao.crane.R

private val screens = listOf(
    R.string.screen_title_find_trips,
    R.string.screen_title_my_trips,
    R.string.screen_title_saved_trips,
    R.string.screen_title_price_alerts,
    R.string.screen_title_my_account
)

@Composable
fun CraneDrawer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, top = 30.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.ic_crane_drawer), contentDescription = null)

        for (screen in screens) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = screen),
                style = MaterialTheme.typography.h4
            )
        }
    }
}