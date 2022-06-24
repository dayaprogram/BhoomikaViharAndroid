package com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyFragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bhoomikabihar.surveyapp.Activity.InputSubsidy.InputSubsidyFragments.InputSubsidyViewModel
import com.bhoomikabihar.surveyapp.Activity.PdfViewActivity
import com.bhoomikabihar.surveyapp.Comman.GPSTracker
import com.bhoomikabihar.surveyapp.Model.Dropdown
import com.bhoomikabihar.surveyapp.R
import com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyActivity

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class
FarmerDetailsForAprvFragment : Fragment() {
    private var gps: GPSTracker? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    lateinit var radioOptionButton: RadioButton
    lateinit var radioBtnGroupMcq: RadioGroup
    var actionCode: Int = 0
    var reasonCode: Int = 0
    var reasonText: String = ""
    private lateinit var viewModel: InputSubsidyViewModel

    companion object {
        fun newInstance() = FarmerDetailsForAprvFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_farmer_details_for_aprv, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = InputSubsidyViewModel(requireActivity().application)

        radioBtnGroupMcq = view.findViewById(R.id.radio_group) as RadioGroup

        val actionList = ArrayList<Dropdown>()
        actionList.addAll(
            listOf(
                Dropdown(
                    0,
                    "Select Action",

                    ),
                Dropdown(
                    1,
                    "Accept",
                ),
                Dropdown(
                    2,
                    "Reject",
                )
            )
        )
        var actionNameList: List<String?> = actionList.map { it.name }
        val spinnerAction = view.findViewById<Spinner>(R.id.spinnerAction)
        val dataAdapterAction =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, actionNameList)
        dataAdapterAction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (spinnerAction != null) {
            spinnerAction.adapter = dataAdapterAction
            spinnerAction.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?, position: Int, id: Long
                ) {
                    actionCode = actionList.find {
                        it.name == parent.getItemAtPosition(
                            position
                        ).toString()
                    }!!.id
                    getReasonSpinner(actionCode)
                    if (actionCode != 0) {
                        Toast.makeText(
                            requireContext(),
                            parent.getItemAtPosition(position).toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(
                        requireContext(),
                        "Please select proper Action from Dropdown !", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        val buttonViewDoc = view.findViewById<Button>(R.id.buttonViewDoc)
//        buttonViewDoc.setOnClickListener {
//            val fragment: Fragment = ViewFarmerDocumetFragment.newInstance()
//            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
//            val fragmentTransaction: FragmentTransaction =
//                fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.container, fragment)
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }
        buttonViewDoc.setOnClickListener {
            val regNo = requireArguments().getString("regId")
            val intent = Intent(requireView().context, PdfViewActivity::class.java)
            val filename = regNo + "KF"
            intent.putExtra("filePath", "$filename.pdf")
            requireView().context.startActivity(intent)
            // requireActivity().finish()
        }
        val btnClickMe = view.findViewById<Button>(R.id.actionSubmit)
        btnClickMe.setOnClickListener {
            val selectedId: Int = radioBtnGroupMcq.checkedRadioButtonId
            val soilTest: String
            Log.e("radioOptionSelcted", "radioOptionselectedId---> $selectedId")
            if (selectedId < 0) {
                Toast.makeText(
                    requireContext(),
                    "Please Select Any One Option !", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                radioOptionButton = view.findViewById<RadioButton>(selectedId)
                soilTest = radioOptionButton.text.toString()

            }
            if (actionCode == 0) {
                Toast.makeText(
                    requireContext(),
                    "Please Take Any Action !", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (reasonCode == 0) {
                Toast.makeText(
                    requireContext(),
                    "Please select Reason !", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val textRemarks = view.findViewById<TextView>(R.id.textRemarks).text.toString()
            if (textRemarks == "") {
                Toast.makeText(
                    requireContext(),
                    "Please enter proper remarks !", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                if (textRemarks.length <= 20) {
                    Toast.makeText(
                        requireContext(),
                        "Remarks Should greater than 20 Characters !", Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }
            val regNo = requireArguments().getString("regId")
            val appNo = requireArguments().getString("appId")
            gps = GPSTracker(requireContext())
            if (gps!!.canGetLocation()) {
                latitude = gps!!.getLatitude()
                longitude = gps!!.getLongitude()
                Toast.makeText(
                    requireContext(),
                    "Your Location is - \nLat: $latitude\nLong: $longitude", Toast.LENGTH_SHORT
                ).show()
            } else {
                gps!!.showSettingsAlert()
            }

            var IMEINumber: String = ""
            val permissionCheck =
                ContextCompat.checkSelfPermission(
                    requireActivity().applicationContext,
                    Manifest.permission.READ_PHONE_STATE
                )

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    REQUEST_READ_PHONE_STATE
                )
                return@setOnClickListener
            } else {
                try {
                    val telephonyManager =
                        requireActivity().getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
                    IMEINumber =
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            // telephonyManager.imei
                            Settings.Secure.getString(
                                requireContext().contentResolver,
                                Settings.Secure.ANDROID_ID
                            )

                        } else {
                            telephonyManager.line1Number
                            telephonyManager.deviceId
                        }
                } catch (ex: Exception) {
                    Log.e("Imei Issue InputSub", "Problem in getting IMEI No. !")
                }

            }



            if (actionCode == 1) {
                viewModel.setApplicationAction(
                    actionCode,
                    reasonCode,
                    textRemarks,
                    regNo!!,
                    appNo!!,
                    soilTest,
                    latitude,
                    longitude,
                    IMEINumber
                )
                val fragment: Fragment = LandDetailsForApproveFragment.newInstance()
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction =
                    fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            } else if (actionCode == 2) {

                var formatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                    current.format(formatter)
                } else {
                    var date = Date()
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    formatter.format(date)
                }
                viewModel.setApplicationActionReject(
                    actionCode,
                    reasonText,
                    textRemarks,
                    regNo!!,
                    appNo!!,
                    soilTest,
                    latitude,
                    longitude,
                    IMEINumber,
                    formatted.toString()
                )

                Toast.makeText(
                    requireContext(),
                    "Application Rejected and save successfully.", Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(requireView().context, InputSubsidyActivity::class.java)
                requireView().context.startActivity(intent)
                requireActivity().finish()
            }
        }


    }

    fun getReasonSpinner(actionCode: Int) {
        //var reasonCode = 0
        val reasonList = ArrayList<Dropdown>()
        if (actionCode == 1) {
            reasonList.addAll(
                listOf(
                    Dropdown(
                        1,
                        "Approved",
                    )
                )
            )
        }
        if (actionCode == 2) {
            reasonList.addAll(
                listOf(
                    Dropdown(
                        0,
                        "Select Reason",
                    ),
                    Dropdown(
                        1,
                        "जमीन विवरण में त्रुटि",
                    ),
                    Dropdown(
                        2,
                        "व्यक्तिगत विवरण में त्रुटि",
                    ),
                    Dropdown(
                        3,
                        "आधार संख्या में त्रुटि",
                    ),
                    Dropdown(
                        4,
                        "एक हीं जमीन पे एक से ज्यादा आवेदन",
                    ),
                    Dropdown(
                        5,
                        "अन्य कारण",
                    )
                )
            )
        }
        var reasonNameList: List<String?> = reasonList.map { it.name }
        val spinnerReason = requireView().findViewById<Spinner>(R.id.spinnerReason)

        val dataAdapterAction =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, reasonNameList)
        dataAdapterAction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerReason.adapter = dataAdapterAction
        spinnerReason?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                reasonCode = reasonList.find {
                    it.name == parent.getItemAtPosition(
                        position
                    ).toString()
                }?.id!!

                reasonText = reasonList.find {
                    it.name == parent.getItemAtPosition(
                        position
                    ).toString()
                }?.name!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    requireContext(),
                    "Please select proper Reason from Dropdown !", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    var REQUEST_READ_PHONE_STATE: Int = 1
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_READ_PHONE_STATE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    requireContext(),
                    "PERMISSION_GRANTED",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
            }
        }
    }

}