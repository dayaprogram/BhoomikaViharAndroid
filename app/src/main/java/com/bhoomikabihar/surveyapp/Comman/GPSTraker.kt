package com.bhoomikabihar.surveyapp.Comman

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat


class GPSTracker(context: Context) : Service(), LocationListener {
    private val mContext: Context = context

    // Flag for GPS status
    private var isGPSEnabled = false

    // Flag for network status
    private var isNetworkEnabled = false

    // Flag for GPS status
    private var canGetLocation = false
    private var location: Location? = null
    private var latitude = 0.0
    private var longitude = 0.0

    // Declaring a Location Manager
    private var locationManager: LocationManager? = null

    @JvmName("getLocation1")
    fun getLocation(): Location? {
        try {
            locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager?
            // Getting GPS status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            // Getting network status
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
            } else {
                canGetLocation = true
                if (isNetworkEnabled) {
//                    if (ActivityCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                            this,
//                            Manifest.permission.ACCESS_COARSE_LOCATION
//                        ) != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        //return
//                    }

                    if (ActivityCompat.checkSelfPermission(
                            (mContext as Activity)!!,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            (mContext as Activity)!!, arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ), 10
                        )
                    }

                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
                    Log.d("Network", "Network")
                    if (locationManager != null) {
                        location = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {
                            latitude = location!!.getLatitude()
                            longitude = location!!.getLongitude()
                        }
                    }
                }
                // If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                        )
                        Log.d("GPS Enabled", "GPS Enabled")
                        if (locationManager != null) {
                            location = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location != null) {
                                latitude = location!!.getLatitude()
                                longitude = location!!.getLongitude()
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return location
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     */
    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    /**
     * Function to get latitude
     */
    @JvmName("getLatitude1")
    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.getLatitude()
        }

        // return latitude
        return latitude
    }

    /**
     * Function to get longitude
     */
    @JvmName("getLongitude1")
    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.getLongitude()
        }

        // return longitude
        return longitude
    }

    /**
     * Function to check GPS/Wi-Fi enabled
     * @return boolean
     */
    fun canGetLocation(): Boolean {
        return canGetLocation
    }

    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     */
    fun showSettingsAlert() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(mContext)
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings")
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")
        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings",
            DialogInterface.OnClickListener { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                mContext.startActivity(intent)
            })

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
        // Showing Alert Message
        alertDialog.show()
    }


    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onLocationChanged(p0: Location) {
//        TODO("Not yet implemented")
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = 1000 * 60 * 1 // 1 minute
            .toLong()
    }

    init {
        getLocation()
    }
}