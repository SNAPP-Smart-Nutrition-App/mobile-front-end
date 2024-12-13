package com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.Scan

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.tensorflow.lite.task.vision.classifier.Classifications

class ScanViewModel : ViewModel() {

    private val _scanResultsData = MutableLiveData<List<Classifications>>()
    val scanResultData : LiveData<List<Classifications>> get() = _scanResultsData

    private val _imageUriLiveData = MutableLiveData<Uri>()
    val imageUriLiveData: LiveData<Uri> get() = _imageUriLiveData

    fun setImageUriLiveData(context: Context,uri: Uri?){
        uri?.let {
            _imageUriLiveData.value = it
        }
        if (uri == null){
            Toast.makeText(context,"No image selected",Toast.LENGTH_SHORT).show()
        }
    }

    fun updateScanResults(results: List<Classifications>) {
        _scanResultsData.value = results
    }

    fun getScanResults(): List<Classifications> {
        return scanResultData.value ?: emptyList()
    }
}