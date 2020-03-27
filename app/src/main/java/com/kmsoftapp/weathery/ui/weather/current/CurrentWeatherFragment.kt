package com.kmsoftapp.weathery.ui.weather.current

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.facebook.share.widget.ShareDialog
import com.kmsoftapp.weathery.R
import com.kmsoftapp.weathery.data.db.entity.ImageEntry
import com.kmsoftapp.weathery.internal.Constants.Companion.FACEBOOK_SHARE_CODE
import com.kmsoftapp.weathery.internal.Constants.Companion.TWITTER_SHARE_CODE
import com.kmsoftapp.weathery.internal.inTransaction
import com.kmsoftapp.weathery.ui.base.ScopedFragment
import com.kmsoftapp.weathery.ui.camera.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.image_dialog.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

@SuppressLint("SetTextI18n")
class CurrentWeatherFragment : ScopedFragment(), KodeinAware,
    OnItemClickListener {
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()
    private val cameraViewModelFactory: CameraViewModelFactory by instance()
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var cameraPreviewViewModel: CameraPreviewViewModel
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        cameraPreviewViewModel =
            ViewModelProvider(this, cameraViewModelFactory).get(CameraPreviewViewModel::class.java)
        fab_camera.setOnClickListener { loadFragment(CameraPreviewFragment()) }
        setHasOptionsMenu(true)
        bindUI()

    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()
        val thumbnails = cameraPreviewViewModel.thumbnails.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })
        thumbnails.observe(viewLifecycleOwner, Observer { thumbnail ->
            if (thumbnail == null) return@Observer
            displayThumbnailList(thumbnail)
        })
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            group_loading.visibility = View.GONE
            updateTemperatures(it.temperature, it.feelslike)
            updateWind(it.wind_dir, it.wind_speed)
            updatePrecipitation(it.wind_speed)
        })
    }

    private fun displayThumbnailList(thumbnails: List<ImageEntry>?) {
        group_loading.visibility = View.GONE
        setupRecyclerView()
        Log.e("ListImageEntry", thumbnails!!.size.toString())
        recyclerview_thambnails.adapter =
            ThumbnailAdapter(
                thumbnails as ArrayList<ImageEntry>,
                activity!!,
                this
            )
    }

    private fun setupRecyclerView() {
        recyclerview_thambnails.layoutManager = GridLayoutManager(activity, 3)
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
//        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature°C"
        textView_feels_like_temperature.text = "Feels like $feelsLike°C"
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        textView_precipitation.text = "Precipitation: $precipitationVolume" + "km"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        textView_wind.text = "Wind: $windDirection, $windSpeed" + "km"
    }

    private fun loadFragment(fragment: Fragment) {
        (context as AppCompatActivity).supportFragmentManager.inTransaction {
            add(R.id.nav_host_fragment, fragment).addToBackStack(null)

        }
    }

    override fun onItemClicked(imageUri: String) {
        showImageDialog(imageUri)
    }

    private fun showImageDialog(imageUri: String) {
        val imageDialog = context?.let { Dialog(it) }
        imageDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        imageDialog?.setContentView(R.layout.image_dialog)
        imageDialog?.show()
        imageDialog!!.image_button_facebook.setOnClickListener {
            shareImage(
                imageUri,
                FACEBOOK_SHARE_CODE
            )
        }
        imageDialog.image_button_twitter.setOnClickListener {
            shareImage(
                imageUri,
                TWITTER_SHARE_CODE
            )
        }
        displayImage(imageDialog, imageUri)
    }

    private fun shareImage(imageUri: String, SOCIAL_MEDIA_CODE: Int) {
        when (SOCIAL_MEDIA_CODE) {
            FACEBOOK_SHARE_CODE -> {
                ShareDialog.show(this, viewModel.shareImageViaFacebook(0, imageUri))
            }

            TWITTER_SHARE_CODE -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_STREAM, imageUri)
                intent.type = "image/*"
                intent.setPackage("com.twitter.android")
                startActivity(intent)

            }
        }

    }

    private fun displayImage(dialog: Dialog?, imageUri: String) {
        dialog!!.dialog_image_iv.setImageURI(Uri.parse(imageUri))
        Log.e("posterUtl", imageUri)
    }

    //    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater): Boolean {
//        val view: View = inflater.inflate(R.menu.menu, menu)
//        return
//    }

}
