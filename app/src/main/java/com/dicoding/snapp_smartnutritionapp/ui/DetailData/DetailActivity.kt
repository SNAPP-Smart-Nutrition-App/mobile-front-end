package com.dicoding.snapp_smartnutritionapp.ui.DetailData

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.dicoding.snapp_smartnutritionapp.R
import com.dicoding.snapp_smartnutritionapp.data.response.Cobacoba.ListEventsItem
import com.dicoding.snapp_smartnutritionapp.databinding.ActivityDetailBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import androidx.core.content.ContextCompat
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.view.View
import com.dicoding.snapp_smartnutritionapp.ui.LoadingDialog

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val detailEventViewModel by viewModels<DetailViewModel>()

    private val _eventDetail = MutableLiveData<ListEventsItem>()
    val eventDetail: LiveData<ListEventsItem> = _eventDetail
    companion object {
        const val EVENT_ID = "233123123"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Menampilkan back arrow di action bar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Detail"
        }

        val eventId = intent.getIntExtra(EVENT_ID,0)
        
        println("EventID: $eventId")
        
        detailEventViewModel.getEvents(eventId)
        
        detailEventViewModel.eventList.observe(this) { eventList ->
            eventList?.firstOrNull()?.let { event ->
                with(binding) {
                    TitleViewDetailData.text = event.name
                    Glide.with(imageView.context)
                        .load(event.imageLogo)
                        .into(imageView)
                }
            }
        }

        detailEventViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setupBarChart()

        // Ambil referensi semua ProgressBar
        val progressBarMain = findViewById<ProgressBar>(R.id.ProgressBarMain)
        val progressBar1 = findViewById<ProgressBar>(R.id.progressBar1)
        val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)
        val progressBar3 = findViewById<ProgressBar>(R.id.progressBar3)
        val progressBar4 = findViewById<ProgressBar>(R.id.progressBar4)

        // Fungsi untuk membuat animasi
        fun animateProgress(progressBar: ProgressBar, progress: Int) {
            ObjectAnimator.ofInt(progressBar, "progress", 0, progress)
                .apply {
                    duration = 2000 // Durasi animasi dalam milidetik
                    interpolator = DecelerateInterpolator()
                    start()
                }
        }

        // Jalankan animasi untuk setiap ProgressBar
        animateProgress(progressBar1, 95)
        animateProgress(progressBar2, 95)
        animateProgress(progressBar3, 50)
        animateProgress(progressBar4, 40)
        animateProgress(progressBarMain, 95)
    }
    private fun setupBarChart() {
        val barChart = findViewById<BarChart>(R.id.barChart)

        // Data untuk chart
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 950f)) // Calories
        entries.add(BarEntry(1f, 500f)) // Carbs
        entries.add(BarEntry(2f, 400f)) // Protein
        entries.add(BarEntry(3f, 400f)) // Fat

        val dataSet = BarDataSet(entries, "Nutrisi")
        dataSet.color = ContextCompat.getColor(this, R.color.Primary)

        // Kustomisasi tampilan
        val data = BarData(dataSet)
        barChart.data = data

        // Konfigurasi chart
        barChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            setDrawGridBackground(false)

            // Konfigurasi X axis
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                valueFormatter = IndexAxisValueFormatter(arrayOf("Calories", "Carbs", "Protein", "Fat"))
                granularity = 1f
            }

            // Konfigurasi Y axis
            axisLeft.apply {
                axisMinimum = 0f
                axisMaximum = 1000f
                setDrawGridLines(true)
            }
            axisRight.isEnabled = false

            // Animasi
            animateY(2000)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            LoadingDialog.show(this)
        } else {
            LoadingDialog.hide()
        }
    }

}