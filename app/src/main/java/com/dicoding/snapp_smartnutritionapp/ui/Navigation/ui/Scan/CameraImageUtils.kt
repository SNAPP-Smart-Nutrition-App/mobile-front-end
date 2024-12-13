package com.dicoding.snapp_smartnutritionapp.ui.Navigation.ui.Scan


import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.dicoding.snapp_smartnutritionapp.ui.DetailData.DetailActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.Locale

class CameraImageUtils(val context: Context) {
    private lateinit var imageCapture: ImageCapture


    fun startCamera(lifecycleOwner: LifecycleOwner, vararg useCase: UseCase?) {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA


            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    imageCapture,
                    *useCase
                )
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Log.e("Camera", "Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun capture() {
        val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis())
        val storageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "SNAPP-Nutriscan")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val photoFile = File(storageDir, "$name.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("imageUri", savedUri.toString())
                    context.startActivity(intent)
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                    Toast.makeText(
                        context,
                        "Error: Image cannot be saved",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

    }

    fun loadImageFromUri(imageUri: String, imageView: ImageView) {
        try {
            TODO("Load image from uri to imageview in another activity")
        } catch (e: Exception) {

        }
    }

    fun uploadToModel(imageFile : File, descriptionText : String) {
        val reqBody = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, reqBody)

        val description = RequestBody.create("text/plain".toMediaTypeOrNull(), descriptionText)
    }
}