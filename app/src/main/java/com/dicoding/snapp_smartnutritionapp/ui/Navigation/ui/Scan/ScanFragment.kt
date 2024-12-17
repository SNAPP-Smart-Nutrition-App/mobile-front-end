package com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.Scan

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicoding.snapp_smartnutritionapp.databinding.FragmentScanBinding
import com.dicoding.snapp_smartnutritionapp.ui.DetailData.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.concurrent.Executors

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var cameraUtils: CameraImageUtils
    private var imageCapture: ImageCapture? = null
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )
    private val viewModel: ScanViewModel by viewModels()

    fun cameraStarterPermission() {
        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                var permissionGranted = true
                permissions.entries.forEach {
                    if (it.key in REQUIRED_PERMISSIONS && !it.value) {
                        permissionGranted = false
                    }
                }
                if (!permissionGranted) {
                    Toast.makeText(
                        requireContext(),
                        "Permission request denied",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    imageCapture = ImageCapture.Builder().build()

                    imageClassifierHelper = ImageClassifierHelper(
                        context = requireContext(),
                        classifierListener = object : ImageClassifierHelper.ClassifierListener {
                            override fun onError(error: String) {
                                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                            }

                            override fun onResults(
                                results: List<Classifications>?,
                                inferenceTime: Long
                            ) {
                                GlobalScope.launch(Dispatchers.Main) {
                                    results?.let { it ->
                                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                            println(it)
                                            val sortedCategories =
                                                it[0].categories.sortedByDescending { it?.score }
                                            val displayResult =
                                                sortedCategories.joinToString("\n") {
                                                    "${it.label} " + NumberFormat.getPercentInstance()
                                                        .format(it.score).trim()
                                                }
                                            binding.lessDetailResult.text = displayResult
                                        } else {
                                            binding.lessDetailResult.text = "No Result"
                                        }
                                    }

                                }
                            }
                        }
                    )
                    cameraUtils = CameraImageUtils(requireContext())
                    val resolutionSelector =
                        ResolutionSelector.Builder()
                            .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                            .build()
                    val imageAnalyzer =
                        ImageAnalysis.Builder().setResolutionSelector(resolutionSelector)
                            .setTargetRotation(binding.viewfinder.display.rotation)
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                            .build()
                    imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                        imageClassifierHelper.classifyImage(image)
                    }
                    val preview = Preview.Builder().build().also {
                        it.surfaceProvider = binding.viewfinder.surfaceProvider
                    }

                    cameraUtils.startCamera(this, preview, imageAnalyzer)
                }
            }
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            data?.data?.let { uri ->
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("imageUri", uri.toString())

//                viewModel.uploadImage(requireContext(), uri)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scanViewModel =
            ViewModelProvider(this)[ScanViewModel::class.java]
        cameraStarterPermission()

        _binding = FragmentScanBinding.inflate(inflater, container, false)

        binding.captureButton.setOnClickListener {
            cameraUtils.capture()

        }
        binding.openGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}