package com.example.assorted.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _lightCheck = MutableStateFlow(false)
     val lightCheck : StateFlow<Boolean> get() = _lightCheck

    fun setLightCheck(){
        _lightCheck.value = !lightCheck.value
    }
}