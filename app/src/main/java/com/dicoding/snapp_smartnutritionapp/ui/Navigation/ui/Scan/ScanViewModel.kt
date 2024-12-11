package com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.Scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.tensorflow.lite.task.vision.classifier.Classifications

class ScanViewModel : ViewModel() {

    private val _scanResultsData = MutableLiveData<List<Classifications>>()
    val scanResultData : LiveData<List<Classifications>> get() = _scanResultsData

    fun updateScanResults(results: List<Classifications>) {
        _scanResultsData.value = results
    }

    fun getScanResults(): List<Classifications> {
        return scanResultData.value ?: emptyList()
    }
}