package com.kmsoftapp.weathery.ui.camera

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kmsoftapp.weathery.R
import com.kmsoftapp.weathery.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.kmsoftapp.weathery.ui.base.ScopedFragment
import com.kmsoftapp.weathery.ui.weather.current.CurrentWeatherViewModel
import com.kmsoftapp.weathery.ui.weather.current.CurrentWeatherViewModelFactory
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Mode
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class CameraPreviewFragment : ScopedFragment(), KodeinAware {

    private lateinit var location: String
    private lateinit var temperatureText: String
    private lateinit var feelsLikeText: String
    private lateinit var windText: String
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    private val cameraViewModelFactory: CameraViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var cameraViewModel: CameraPreviewViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        cameraViewModel =
            ViewModelProvider(this, cameraViewModelFactory).get(CameraPreviewViewModel::class.java)
        camera.setLifecycleOwner(viewLifecycleOwner)
        camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                val returnedBitmap: Bitmap = cameraViewModel.overlayTextOnImage(
                    cameraViewModel.convertByteArrayToBitmap(result),
                    temperatureText,
                    feelsLikeText,
                    windText,
                    location
                )
                updateUI(returnedBitmap)
                btn_save.setOnClickListener {
                    saveImage(returnedBitmap)
//                    hideButtons()
                }
                btn_discard.setOnClickListener {
                    discardImage()

//                    hideButtons()
                }
            }

            override fun onCameraOpened(options: CameraOptions) {
                super.onCameraOpened(options)
                bindUI()
            }


        })
        camera.mode = Mode.PICTURE
        capturePictureSnapshot.setOnClickListener {
            camera.takePicture()
        }

    }

//    private fun hideButtons() {
//
//    }

    private fun discardImage() {
        btn_discard.visibility = View.INVISIBLE
        btn_save.visibility = View.INVISIBLE
        imageView.setImageBitmap(null)
        Toast.makeText(activity, "Cancelled Successfully", Toast.LENGTH_LONG).show()
    }

    private fun saveImage(bitmapImage: Bitmap) {
        btn_discard.visibility = View.INVISIBLE
        btn_save.visibility = View.INVISIBLE
        imageView.setImageBitmap(null)
        cameraViewModel.saveToInternalStorage(bitmapImage, context!!)
        cameraViewModel.insertThumbnail(
            cameraViewModel.saveToInternalStorage(
                bitmapImage,
                context!!
            )
        )
    }

    fun updateUI(returnedBitmap: Bitmap) {
        btn_save.visibility = View.VISIBLE
        btn_discard.visibility = View.VISIBLE
        imageView.setImageBitmap(returnedBitmap)
    }

    private fun bindUI() = launch {

        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)

        }
        )

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            showTextOnCameraPreview(it)

        })
    }

    private fun showTextOnCameraPreview(it: UnitSpecificCurrentWeatherEntry) {
        val watermarkText: String =
            "Temperature is: " + it.temperature.toString() + "°C" + "\n" +
                    "Feels like: " + it.feelslike.toString() + "\n" +
                    "Wind: " + it.wind_dir + it.wind_speed + "KM\n" +
                    "City: " + location + "\n"
        watermark.text = watermarkText
        temperatureText = it.temperature.toString()
        feelsLikeText = it.feelslike.toString()
        windText = it.wind_dir.toString() + it.wind_speed
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
        this.location = location
    }
}