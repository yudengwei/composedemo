package com.abiao.crane.viewmodel

import android.window.SplashScreen
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abiao.crane.compose.MAX_PEOPLE
import com.abiao.crane.data.CalendarState
import com.abiao.crane.data.DestinationsRepository
import com.abiao.crane.data.ExploreModel
import com.abiao.crane.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val destinationsRepository : DestinationsRepository,
    @IODispatcher private val IODispatch : CoroutineDispatcher
) : ViewModel() {

    val shownSplash = mutableStateOf(SplashState.Shown)

    val calendarState = CalendarState()

    private val _suggestedDestinations = MutableLiveData<List<ExploreModel>>()
    val suggestedDestinations: LiveData<List<ExploreModel>>
        get() = _suggestedDestinations

    init {
        _suggestedDestinations.value = destinationsRepository.destinations
    }

    fun updatePeople(people: Int) {
        viewModelScope.launch {
            if (people > MAX_PEOPLE) {
                _suggestedDestinations.value = emptyList()
            } else {
                val newDestinations = withContext(IODispatch) {
                    destinationsRepository.destinations
                        .shuffled(Random(people * (1..100).shuffled().first()))
                }
                _suggestedDestinations.value = newDestinations
            }
        }
    }
}

enum class SplashState { Shown, Completed }