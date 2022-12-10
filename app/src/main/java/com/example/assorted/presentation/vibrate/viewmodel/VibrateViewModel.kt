package com.example.assorted.presentation.vibrate.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VibrateViewModel @Inject constructor() : ViewModel() {
    //진동 상태
    private var _vibrateState = MutableStateFlow(0)
    val vibrateState: StateFlow<Int> get() = _vibrateState

    fun plusVibrateState() {
        if (vibrateState.value != 4) _vibrateState.value++
    }

    fun minusVibrateState() {
        if (vibrateState.value != 0) _vibrateState.value--
    }

    //클릭시 뷰 변경
    val vibrateStateView = mutableListOf(false, false, false, false, false)
    var vibrateUpDown = false
    fun changeTrueVibrateView(index: Int) {
        vibrateStateView[index] = true
    }

    fun changeFalseVibrateView(index: Int) {
        vibrateStateView[index + 1] = false
    }
}