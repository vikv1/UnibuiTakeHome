package com.example.unibuitakehome2

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tomtom.sdk.location.GeoPoint
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.image.ImageFactory
import com.tomtom.sdk.map.display.marker.Marker
import com.tomtom.sdk.map.display.marker.MarkerOptions
import com.tomtom.sdk.map.display.ui.MapFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Nearby.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nearby : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val apiKey = BuildConfig.TOMTOM_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mapOptions = MapOptions(mapKey = apiKey)
        val mapFragment = MapFragment.newInstance(mapOptions)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

        mapFragment.getMapAsync { tomtomMap: TomTomMap ->
            // Your code goes here
            val omGal = GeoPoint(36.972813044976725,  -122.0260341923235)
            val madSci = GeoPoint(37.50310834049785, -121.94768507405632)

            val begin =
                MarkerOptions(
                    coordinate = omGal,
                    pinImage = ImageFactory.fromResource(R.drawable.pin),
                    balloonText = "Beginning"
                )

            val end =
                MarkerOptions(
                    coordinate = madSci,
                    pinImage = ImageFactory.fromResource(R.drawable.pin),
                    balloonText = "End"
                )




            val beg = tomtomMap.addMarker(begin)
            val e = tomtomMap.addMarker(end)
            tomtomMap.addMarkerClickListener { marker: Marker ->
                if (!marker.isSelected()) {
                    marker.select()
                }
            }

            val marks = listOf(beg, e)

            val cameraOptions =
                CameraOptions(
                    position = omGal,
                    zoom = 7.0,
                    tilt = 45.0,
                    rotation = 0.0,
                )
            tomtomMap.moveCamera(cameraOptions)


            CoroutineScope(Dispatchers.Main).launch {
                delay(5000) // Delay for 5 seconds


                val newCameraOptions =
                    CameraOptions(
                        position = madSci,
                        zoom = 15.0,
                        tilt = 65.0,
                        rotation = 0.0,
                    )
                tomtomMap.animateCamera(newCameraOptions, 3.seconds)

            }

        }

        return inflater.inflate(R.layout.fragment_nearby, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Nearby.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Nearby().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}