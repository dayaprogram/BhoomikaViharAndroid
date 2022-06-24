package com.bhoomikabihar.surveyapp.Activity.VerifyFarmer.ui.VerificationFragments

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
import com.bhoomikabihar.surveyapp.Model.PMKisanVerifyACModel
import com.bhoomikabihar.surveyapp.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PMKisanACVerificationFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var viewModel: PMKisanACVerificationViewModel
    val Aadhar = "Aadhar"

    companion object {
        fun newInstance() = PMKisanACVerificationFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pmkisan_newac_verification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = PMKisanACVerificationViewModel(requireActivity().application)
        // var sessionManager: SessionManager = SessionManager(requireActivity().applicationContext)
        var textAcDeclearation =
            requireView().findViewById<CheckBox>(R.id.textAcDeclearation)
        var buttonVerifyFarmer =
            requireView().findViewById<View>(R.id.buttonVerifySave) as Button
        var farmerVerificationDtl: PMKisanVerifyACModel = PMKisanVerifyACModel()
        textAcDeclearation.text =
            "मेरे द्वारा PM-किसान लाभुक की दी गई उपरोक्त जानकारी सही है एवं मैंने लाभुक के उपरोक्त जानकारी को सत्यापित किया है ।"
        // buttonVerifyFarmer.isClickable = false
//        textAcDeclearation.setOnCheckedChangeListener { compoundButton, v ->
//            buttonVerifyFarmer.isClickable = v
//        }
        var buttonViewApplication =
            requireView().findViewById<View>(R.id.btn_view_app) as Button
        var buttonViewDoc =
            requireView().findViewById<View>(R.id.btn_view_doc) as Button
        var radioGroupDocUpload =
            requireView().findViewById<View>(R.id.radioGroupDocUpload) as RadioGroup
        var radioGroupMoreThanOne =
            requireView().findViewById<View>(R.id.radioGroupMoreThanOne) as RadioGroup
        var radioGroupPersonalInfo =
            requireView().findViewById<View>(R.id.radioGroupPersonalInfo) as RadioGroup
        var radioGroupFamilyInfo =
            requireView().findViewById<View>(R.id.radioGroupFamilyInfo) as RadioGroup
        var radioGroupBankInfo =
            requireView().findViewById<View>(R.id.radioGroupBankInfo) as RadioGroup
        var radioGroupLandInfo =
            requireView().findViewById<View>(R.id.radioGroupLandInfo) as RadioGroup


        var radioGroupRayatInfo =
            requireView().findViewById<View>(R.id.radiorayatInfo) as RadioGroup
        var radioGroupTopolandInfo =
            requireView().findViewById<View>(R.id.radioGrouptopolandInfo) as RadioGroup
        var radioGroupAllInfo =
            requireView().findViewById<View>(R.id.radioGroupAllInfo) as RadioGroup


        buttonViewApplication.setOnClickListener {
            val intent =
                Intent(requireView().context, PMKisanApplicationViewActivity::class.java)
            var regNo: String =
                requireActivity().findViewById<TextView>(R.id.textRegNo).text.toString();
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
            farmerVerificationDtl.MobileNo =
                requireActivity().findViewById<TextView>(R.id.textFMobNo).text.toString()

            val selectedRBACDocVerifyId: Int = radioGroupDocUpload.checkedRadioButtonId
            if (selectedRBACDocVerifyId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACDocVerifyId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ACDocVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "1 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedRBACFamilyBenifyId: Int = radioGroupMoreThanOne.checkedRadioButtonId
            if (selectedRBACFamilyBenifyId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACFamilyBenifyId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ACFamilyBenify = selectedRbText
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
                farmerVerificationDtl.ACPersonalInfoVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "3 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedRBACFamilyInfoVerifyId: Int = radioGroupFamilyInfo.checkedRadioButtonId
            if (selectedRBACFamilyInfoVerifyId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACFamilyInfoVerifyId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ACFamilyInfoVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "4 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBACBankInfoVerifyId: Int = radioGroupBankInfo.checkedRadioButtonId
            if (selectedRBACBankInfoVerifyId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACBankInfoVerifyId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ACBankInfoVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "5 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBACLandInfoVerifyId: Int = radioGroupLandInfo.checkedRadioButtonId
            if (selectedRBACLandInfoVerifyId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACLandInfoVerifyId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ACLandInfoVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "6 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedRBACRayatInfoId: Int = radioGroupRayatInfo.checkedRadioButtonId
            if (selectedRBACRayatInfoId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACRayatInfoId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ACRayatInfoVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "7 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBACTopolandInfoId: Int = radioGroupTopolandInfo.checkedRadioButtonId
            if (selectedRBACTopolandInfoId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACTopolandInfoId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ACTopolandInfoVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "8 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBACAllInfoId: Int = radioGroupAllInfo.checkedRadioButtonId
            if (selectedRBACAllInfoId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBACAllInfoId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ACAllInfoVerify = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "9 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!textAcDeclearation.isChecked) {
                Toast.makeText(
                    requireContext(),
                    "कृषि समन्वयक घोषणा को चुनें!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var gps: GPSTracker? = GPSTracker(requireContext())
            if (gps!!.canGetLocation()) {
                farmerVerificationDtl.ACLatitude = gps!!.getLatitude()
                farmerVerificationDtl.ACLongitude = gps!!.getLongitude()
            } else {
                Toast.makeText(
                    requireContext(),
                    "जीपीएस ऑन करें या जीपीएस जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()
                gps.showSettingsAlert()
                return@setOnClickListener
            }

            farmerVerificationDtl.ACIMEINo = Settings.Secure.getString(
                requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID
            )
            if (farmerVerificationDtl.ACIMEINo == "") {
                Toast.makeText(
                    requireContext(),
                    "फोन की जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (farmerVerificationDtl.ACLatitude == 0.0 || farmerVerificationDtl.ACLongitude == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "जीपीएस ऑन करें या जीपीएस जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            farmerVerificationDtl.ACIMEINo = Settings.Secure.getString(
                requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID
            )
            if (farmerVerificationDtl.ACIMEINo == "") {
                Toast.makeText(
                    requireContext(),
                    "फोन की जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            viewModel.saveApprovedFarmerDetails(farmerVerificationDtl)
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