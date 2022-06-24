package com.bhoomikabihar.surveyapp.Activity.VerifyFarmer.ui.VerificationFragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bhoomikabihar.surveyapp.Activity.PMKisanACVerification.PMKisanApplicationViewActivity
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.PMKisanACVerificationListActivity
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.PMKisanACVerificationViewModel
import com.bhoomikabihar.surveyapp.Comman.GPSTracker
import com.bhoomikabihar.surveyapp.Model.PMKisanVerifyCOModel
import com.bhoomikabihar.surveyapp.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PMKisanCOVerificationFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var viewModel: PMKisanACVerificationViewModel

    companion object {
        fun newInstance() = PMKisanCOVerificationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pmkisan_newco_verification, container, false)
    }

    @SuppressLint("HardwareIds")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = PMKisanACVerificationViewModel(requireActivity().application)
        // var sessionManager: SessionManager = SessionManager(requireActivity().applicationContext)
        var textAcDeclearation =
            requireView().findViewById<CheckBox>(R.id.textAcDeclearation)
        var buttonVerifyFarmer =
            requireView().findViewById<View>(R.id.buttonVerifySave) as Button
        var farmerVerificationDtl: PMKisanVerifyCOModel = PMKisanVerifyCOModel()
        textAcDeclearation.text = " सत्यापन उपरांत मेरे द्वारा उपरोक्त दी गई जानकारी सही है।"

        var buttonViewApplication =
            requireView().findViewById<View>(R.id.btn_view_app) as Button
        var buttonViewDoc =
            requireView().findViewById<View>(R.id.btn_view_doc) as Button
        var radioGroupLandDetails =
            requireView().findViewById<View>(R.id.radioGrouplandDetails) as RadioGroup
        var radioGroupLandDoc =
            requireView().findViewById<View>(R.id.radioGroupLandDoc) as RadioGroup
        var radioGroupPersonalInfo =
            requireView().findViewById<View>(R.id.radioGroupPersonalInfo) as RadioGroup



        buttonViewApplication.setOnClickListener {
            val intent =
                Intent(requireView().context, PMKisanApplicationViewActivity::class.java)
            var regNo: String =
                requireActivity().findViewById<TextView>(R.id.textRegNo).text.toString();
            if (regNo != null) {
                regNo = regNo.substring(0, 13)
            }
            var url =
                "http://164.100.130.206/WebService/Home/PmKisanFormPreview?RegistrationNo=$regNo"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
            //requireActivity().finish()
        }

        buttonViewDoc.setOnClickListener {
            val intent =
                Intent(requireView().context, PMKisanApplicationViewActivity::class.java)
            var regNo: String =
                requireActivity().findViewById<TextView>(R.id.textRegNo).text.toString();
            if (regNo != null) {
                regNo = regNo.substring(0, 13)
            }
            val filename =
                "https://dbtagriculture.bihar.gov.in/regfarmer/pmkisanadmin/ViewDocument.aspx?Rid=$regNo"
            var url = "https://drive.google.com/viewerng/viewer?embedded=true&url=$filename"
            intent.putExtra("URL", url)
            requireView().context.startActivity(intent)
            //requireActivity().finish()
        }

        buttonVerifyFarmer.setOnClickListener {
            farmerVerificationDtl.RegistrationNo =
                requireActivity().findViewById<TextView>(R.id.textRegNo).text.toString()


            val selectedRBLandDetailsId: Int = radioGroupLandDetails.checkedRadioButtonId
            if (selectedRBLandDetailsId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBLandDetailsId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.CODocVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "1 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBACFamilyBenifyId: Int = radioGroupLandDoc.checkedRadioButtonId
            if (selectedRBACFamilyBenifyId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACFamilyBenifyId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.CODocRecent = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "2 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBACPersonalInfoVerifyId: Int = radioGroupPersonalInfo.checkedRadioButtonId
            if (selectedRBACPersonalInfoVerifyId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACPersonalInfoVerifyId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.COApprove = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "3 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            if (!textAcDeclearation.isChecked) {
                Toast.makeText(
                    requireContext(),
                    "अंचलाधिकारी की घोषणा को चुनें!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var gps: GPSTracker? = GPSTracker(requireContext())
            if (gps!!.canGetLocation()) {
                farmerVerificationDtl.COLatitude = gps!!.getLatitude()
                farmerVerificationDtl.COLongitude = gps!!.getLongitude()
            } else {
                Toast.makeText(
                    requireContext(),
                    "जीपीएस ऑन करें या जीपीएस जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()
                gps.showSettingsAlert()
                return@setOnClickListener
            }
            if (farmerVerificationDtl.COLatitude == 0.0 || farmerVerificationDtl.COLongitude == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "जीपीएस ऑन करें या जीपीएस जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }
            farmerVerificationDtl.COIMEINo = Settings.Secure.getString(
                requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID
            )
            if (farmerVerificationDtl.COIMEINo == "") {
                Toast.makeText(
                    requireContext(),
                    "फोन की जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.saveApprovedCOFarmerDetails(farmerVerificationDtl)
        }

        viewModel.liveFarmerVerifyResponse.observe(viewLifecycleOwner, Observer { result ->
            if (result > 0) {
                Toast.makeText(
                    requireContext(),
                    "सत्यापन से सम्बंधित जानकारी सुरक्षित कर ली गई है ।",
                    Toast.LENGTH_LONG
                ).show()
                val intent =
                    Intent(requireView().context, PMKisanACVerificationListActivity::class.java)
                requireView().context.startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(
                    requireContext(),
                    "जानकारी सुरक्षित करने में समस्या हो रही है | पुनः प्रयास करें !",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }


    private var REQUEST_READ_PHONE_STATE: Int = 1;
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val currentDateString: String =
            DateFormat.getDateInstance(DateFormat.FULL).format(c.time)


        val format = SimpleDateFormat("dd-MM-yyyy")
        format.format(c.time)

        val textView = requireView().findViewById(R.id.VerifyDate) as TextView
        textView.text = format.format(c.time)
    }
}