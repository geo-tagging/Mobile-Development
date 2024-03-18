package com.dicoding.geotaggingjbg.ui.camera

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dicoding.geotaggingjbg.databinding.FragmentCameraBinding
import android.Manifest
import android.app.Activity
import android.location.Location
import android.os.Build
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.dicoding.geotaggingjbg.R
import com.dicoding.geotaggingjbg.ViewModelFactory
import com.dicoding.geotaggingjbg.ui.save.SaveFragment
import com.dicoding.geotaggingjbg.ui.utils.createCustomTempFile
import com.dicoding.geotaggingjbg.ui.utils.reduceFileImage
import com.dicoding.geotaggingjbg.ui.utils.uriToFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import id.zelory.compressor.Compressor


class CameraFragment() : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CameraViewModel> {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private var currentImageUri: Uri? = null
    private val cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireActivity(), "Permission request granted", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireActivity(), "Permission request denied", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireActivity(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
        binding.ivCamGallery.setOnClickListener { startGallery() }
        binding.ivCamCapture.setOnClickListener { takePhoto() }
        binding.ivCamClose.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_camera_to_navigation_home)
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI(requireActivity())
        startCameraX()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val imageUri = currentImageUri.toString()
            val bundle = Bundle().apply {
                putString(SaveFragment.EXTRA_FILE, imageUri)
            }
            showFragment(bundle)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCameraX() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.pvCamViewfinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    requireActivity(),
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private fun takePhoto() {
        if (allPermissionsGranted()) {
            // Mendapatkan lokasi pengguna
            getLocation()
        } else {
            // Meminta izin lokasi jika belum diberikan
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocation() {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission granted, proceed to fetch the last known location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Handle the location result
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val elevation = location.altitude

                        // Proceed with image capture and saving
                        takePictureWithLocation(latitude, longitude, elevation)
                    } else {
                        // Handle case where location is null
                        showToast("Failed to retrieve location")
                    }
                }
            .addOnFailureListener { e ->
                // Gagal mendapatkan lokasi
                showToast("Failed to retrieve location: ${e.message}")
            }
        } else {
            // Permission not granted, request the permission from the user
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun takePictureWithLocation(latitude: Double, longitude: Double, elevation: Double) {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(requireActivity())
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val imageUri = output.savedUri ?: Uri.fromFile(photoFile)

                    // Simpan gambar bersama dengan data lokasi
                    val bundle = Bundle().apply {
                        putString(SaveFragment.EXTRA_FILE, imageUri.toString())
                        putDouble("latitude", latitude)
                        putDouble("longitude", longitude)
                        putDouble("elevation", elevation)
                    }
                    showFragment(bundle)
                }

                override fun onError(exc: ImageCaptureException) {
                    showToast("Failed to capture image")
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

//    private fun takePhoto() {
//        val imageCapture = imageCapture ?: return
//        val photoFile = createCustomTempFile(requireActivity())
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(requireActivity()),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val imageUri = output.savedUri ?: Uri.fromFile(photoFile)
//                    val bundle = Bundle().apply {
//                        putString(SaveFragment.EXTRA_FILE, imageUri.toString())
//                    }
//                    showFragment(bundle)
//                }
//
//                override fun onError(exc: ImageCaptureException) {
//                    Toast.makeText(
//                        requireActivity(),
//                        "Gagal mengambil gambar.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    Log.e(TAG, "onError: ${exc.message}")
//                }
//            }
//        )
//    }

    fun showFragment(bundle: Bundle) {
        val saveFragment = SaveFragment()
        saveFragment.arguments = bundle

        requireView().findNavController()
            .navigate(R.id.action_navigation_camera_to_navigation_save, bundle)
    }

    private val orientationEventListener by lazy {
        object : OrientationEventListener(requireActivity()) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> Surface.ROTATION_270
                    in 135 until 225 -> Surface.ROTATION_180
                    in 225 until 315 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageCapture?.targetRotation = rotation
            }
        }
    }

    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }

    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
    }

    private fun hideSystemUI(activity: Activity) {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

//    internal var optionDialogListener: SaveFragment.OnOptionDialogListener =
//        object : SaveFragment.OnOptionDialogListener {
//            override fun onOptionChosen(text: String?, image: String?) {
//// Dipake ketempet lain
//                currentImageUri = image!!.toUri()
//                currentImageUri?.let { uri ->
//                    val imageFile = uriToFile(uri, requireActivity()).reduceFileImage()
////sampe sini
//                    lifecycleScope.launch {
//                        val compressedImage = Compressor.compress(requireContext(), imageFile)
//                        Log.d("Image File", "showImage: ${imageFile.path}")
//                        showLoading(true)

//                        when (text) {
//                            "Object Detection" -> {
//                                viewModel.uploadObjectDetect(compressedImage)
//                                    .observe(this@CameraFragment) { result ->
//                                        if (result != null) {
//                                            when (result) {
//                                                is ResultState.Success -> {
//                                                    showLoading(false)
//
//                                                    val responseBundle = Bundle().apply {
//                                                        // Menambahkan dua list ke dalam Bundle
//                                                        putString(ScanResultObjectFragment.EXTRA_PHOTO, image)
//                                                        putString(ScanResultObjectFragment.EXTRA_RESULT, result.data.prediction)
//                                                    }
//
//                                                    val receiverFragment = ScanResultObjectFragment()
//                                                    receiverFragment.arguments = responseBundle
//
//                                                    view!!.findNavController().navigate(R.id.action_navigation_scan_to_scanResultObjectFragment, responseBundle)
//                                                }
//
//                                                is ResultState.Loading -> {
//                                                    showLoading(true)
//                                                }
//
//                                                is ResultState.Error -> {
//                                                    showLoading(false)
//                                                    showToast("Server Error")
//                                                }
//                                            }
//                                        }
//                                    }
//                            }
//                            else -> {
//                                showToast("Unknown")
//                            }
//                        }
//                    }
//
//
//                }
//            }
//        }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "CameraFragment"
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}