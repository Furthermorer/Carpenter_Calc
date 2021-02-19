package com.carpenter_calc.ui.ArchHeight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArchHeightViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ArchHeight Fragment"
    }
    val text: LiveData<String> = _text
}