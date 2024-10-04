package com.byteflipper.mcbedrock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var isReady: Boolean = false
        private set

    fun onSplashScreenTimeout() {
        isReady = true
    }

    init {
        viewModelScope.launch {
            delay(100L)
            isReady = true
        }
    }
}