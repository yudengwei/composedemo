package com.abiao.lib_nowinandroid.ui.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onInterestsClick: () -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {

}