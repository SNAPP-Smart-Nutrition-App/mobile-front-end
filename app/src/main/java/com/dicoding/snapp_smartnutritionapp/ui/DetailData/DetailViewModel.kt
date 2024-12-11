package com.dicoding.snapp_smartnutritionapp.ui.DetailData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba.EventResponse
import com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba.ListEventsItem
import com.dicoding.snapp_smartnutritionapp.data.retrofit.ApiConfig
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel:ViewModel() {
    //  list Event untuk RecycleView
    private val _eventList = MutableLiveData<List<ListEventsItem>>()
    val eventList: LiveData<List<ListEventsItem>> = _eventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailAcivity"
    }
    //    Tampilkan Event
    fun getEvents(active: Int) {
        _isLoading.value = true
        val client = ApiConfig.getSecondApiService().getEvents(active)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful && response.body() != null){
                    val responseBody =  response.body()!!
                    _eventList.value = responseBody.listEvents
                }else{
                    Log.e(TAG,"onFailure: ${response.message()}" )
                }
                _isLoading.value = false
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                _isLoading.value = false
            }
        })
    }
}