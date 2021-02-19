package com.carpenter_calc.ui.SlopeCoefficient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlopeCoefficientViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is SlopeCoefficient Fragment"
    }
    val text: LiveData<String> = _text
}