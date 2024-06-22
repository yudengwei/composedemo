package com.abiao.crane.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abiao.crane.data.DestinationsRepository
import com.abiao.crane.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val destinationsRepository : DestinationsRepository,
    @IODispatcher private val IODispatch : CoroutineDispatcher
) : ViewModel() {
}