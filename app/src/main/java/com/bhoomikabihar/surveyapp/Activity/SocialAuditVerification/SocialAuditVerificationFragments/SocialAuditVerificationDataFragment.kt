package com.bhoomikabihar.surveyapp.Activity.VerifyFarmer.ui.VerificationFragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bhoomikabihar.surveyapp.R


class SocialAuditVerificationDataFragment : Fragment() {
    companion object {
        fun newInstance() = SocialAuditVerificationDataFragment()
    }


    private val MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_farmer_verify_data, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //trigger 'loadIMEI'
        loadIMEI()
        /** Fading Transition Effect  */
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * Called when the 'loadIMEI' function is triggered.
     */
    private fun loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_PHONE_STATE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission()
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs()
        }
    }

    /**
     * Requests the READ_PHONE_STATE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private fun requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                Activity(),
                Manifest.permission.READ_PHONE_STATE
            )
        ) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            AlertDialog.Builder(requireContext())
                .setTitle("Permission Request")
                .setMessage("ProvidePermission")
//            getString(R.string.permission_read_phone_state_rationale)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { _, _ -> //re-request
                        ActivityCompat.requestPermissions(
                            requireActivity(), arrayOf(Manifest.permission.READ_PHONE_STATE),
                            MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
                        )
                    })
                .setIcon(R.drawable.ic_menu_manage)
                .show()
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.READ_PHONE_STATE),
                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
            )
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
                doPermissionGrantedStuffs()
            } else {
                alertAlert("Permission Requred")
//                        getString(R.string.permissions_not_granted_read_phone_state)
            }
        }
    }

    private fun alertAlert(msg: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Request")
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    // do somthing here
                })
            .setIcon(R.drawable.ic_baseline_person)
            .show()
    }

    @SuppressLint("HardwareIds")
    private fun doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        val tm = requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        val IMEINumber = tm!!.deviceId


        /************************************************
         * **********************************************
         * This is just an icing on the cake
         * the following are other children of TELEPHONY_SERVICE
         *
         * //Get Subscriber ID
         * String subscriberID=tm.getDeviceId();
         *
         * //Get SIM Serial Number
         * String SIMSerialNumber=tm.getSimSerialNumber();
         *
         * //Get Network Country ISO Code
         * String networkCountryISO=tm.getNetworkCountryIso();
         *
         * //Get SIM Country ISO Code
         * String SIMCountryISO=tm.getSimCountryIso();
         *
         * //Get the device software version
         * String softwareVersion=tm.getDeviceSoftwareVersion()
         *
         * //Get the Voice mail number
         * String voiceMailNumber=tm.getVoiceMailNumber();
         *
         *
         * //Get the Phone Type CDMA/GSM/NONE
         * int phoneType=tm.getPhoneType();
         *
         * switch (phoneType)
         * {
         * case (TelephonyManager.PHONE_TYPE_CDMA):
         * // your code
         * break;
         * case (TelephonyManager.PHONE_TYPE_GSM)
         * // your code
         * break;
         * case (TelephonyManager.PHONE_TYPE_NONE):
         * // your code
         * break;
         * }
         *
         * //Find whether the Phone is in Roaming, returns true if in roaming
         * boolean isRoaming=tm.isNetworkRoaming();
         * if(isRoaming)
         * phoneDetails+="\nIs In Roaming : "+"YES";
         * else
         * phoneDetails+="\nIs In Roaming : "+"NO";
         *
         *
         * //Get the SIM state
         * int SIMState=tm.getSimState();
         * switch(SIMState)
         * {
         * case TelephonyManager.SIM_STATE_ABSENT :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_PIN_REQUIRED :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_PUK_REQUIRED :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_READY :
         * // your code
         * break;
         * case TelephonyManager.SIM_STATE_UNKNOWN :
         * // your code
         * break;
         *
         * }
         */
        // Now read the desired content to a textview.

        Toast.makeText(
            requireContext(), "imeiNo :-" + IMEINumber,
            Toast.LENGTH_LONG
        ).show();
//        loading_tv2 = requireView().findViewById(R.id.loginId) as TextView?
//        loading_tv2!!.text = IMEINumber
    }


}

