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
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.PMKisanListVerifyActivity
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.PMKisanVerifyViewModel
import com.bhoomikabihar.surveyapp.Comman.GPSTracker
import com.bhoomikabihar.surveyapp.Model.PMKisanEligibleVerification
import com.bhoomikabihar.surveyapp.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PMKisanVerificationFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var viewModel: PMKisanVerifyViewModel
    val Aadhar = "Aadhar"

    companion object {
        fun newInstance() = PMKisanVerificationFragment()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pmkisan_verification, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = PMKisanVerifyViewModel(requireActivity().application)
        // var sessionManager: SessionManager = SessionManager(requireActivity().applicationContext)
        var textAcDeclearation =
            requireView().findViewById<CheckBox>(R.id.textAcDeclearation)
        var buttonVerifyFarmer =
            requireView().findViewById<View>(R.id.buttonVerifySave) as Button
        var farmerVerificationDtl: PMKisanEligibleVerification = PMKisanEligibleVerification()
        textAcDeclearation.text =
            "मेरे द्वारा PM-किसान लाभुक की दी गई उपरोक्त जानकारी सही है एवं मैंने लाभुक के उपरोक्त जानकारी को सत्यापित किया है ।"
        // buttonVerifyFarmer.isClickable = false
//        textAcDeclearation.setOnCheckedChangeListener { compoundButton, v ->
//            buttonVerifyFarmer.isClickable = v
//        }

        var radioGroupEntitlement =
            requireView().findViewById<View>(R.id.radioGroupEntitlement) as RadioGroup
        var radioGroupIncomeTax =
            requireView().findViewById<View>(R.id.radioGroupIncomeTax) as RadioGroup
        var radioGroupMinister =
            requireView().findViewById<View>(R.id.radioGroupMinister) as RadioGroup
        var radioGroupGovtEmp =
            requireView().findViewById<View>(R.id.radioGroupGovtEmp) as RadioGroup
        var radioGroupRetired =
            requireView().findViewById<View>(R.id.radioGroupRetired) as RadioGroup
        var radioGroupConstitutionalPost =
            requireView().findViewById<View>(R.id.radioGroupConstitutionalPost) as RadioGroup
        var radioGroupProfessional =
            requireView().findViewById<View>(R.id.radioGroupProfessional) as RadioGroup
        var radioGroupPhyVerifResponse =
            requireView().findViewById<View>(R.id.radioGroupPhyVerifResponse) as RadioGroup
        var radioGroupFamilyGetBen =
            requireView().findViewById<View>(R.id.radioGroupFamilyGetBen) as RadioGroup
        var radioGroupLandHave =
            requireView().findViewById<View>(R.id.radioGroupLandHave) as RadioGroup


        val districtList = viewModel.getDistrictList()
        districtList.sortBy { it.name }
        var districtNameList: List<String?> = districtList.map { it.name }
        val spinnerDistrict = requireView().findViewById<Spinner>(R.id.spinnerIneligibleReason)

        val dataAdapterDistrict =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, districtNameList)
        // Drop down layout style - list view with radio button
        dataAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Creating adapter for spinner
        // attaching data adapter to spinner
        spinnerDistrict.adapter = dataAdapterDistrict
        var distCode = 0
        spinnerDistrict.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                distCode = districtList.find {
                    it.name == parent.getItemAtPosition(
                        position
                    ).toString()
                }?.id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    context,
                    "Please select proper District from Dropdown !", Toast.LENGTH_SHORT
                ).show()
            }

        }

        var c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val format = SimpleDateFormat("dd-MM-yyyy")
        var IneligibleReasonText = requireView().findViewById(R.id.textIneligibleReason) as TextView
        var dobTab = requireView().findViewById(R.id.VerifyDate) as TextView

        dobTab.setOnClickListener {
            val dpd = DatePickerDialog(
                requireActivity(),
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    c = Calendar.getInstance()
                    c.set(Calendar.YEAR, year)
                    c.set(Calendar.MONTH, monthOfYear)
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    format.format(c.time)
                    dobTab = requireView().findViewById(R.id.VerifyDate) as TextView
                    dobTab.text = format.format(c.time)
                }, year, month, day
            )
            val cf = Calendar.getInstance();
            dpd.datePicker.maxDate = cf.timeInMillis
            dpd.show()
        }


        radioGroupPhyVerifResponse.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            val radio: RadioButton = group.findViewById(checkedId)
            if (radio.text.toString() == "Eligible") {
                IneligibleReasonText.visibility = View.GONE
                spinnerDistrict.visibility = View.GONE
                var c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                format.format(c.time)
                dobTab.text = format.format(c.time)
                dobTab.isClickable = false
            }
            if (radio.text.toString() == "Death") {
                IneligibleReasonText.visibility = View.GONE
                spinnerDistrict.visibility = View.GONE
                dobTab.text = "";
                dobTab.hint = "मृत्यु की तारीख";
                dobTab.isClickable = true
            }
            if (radio.text.toString() == "Ineligible") {
                IneligibleReasonText.visibility = View.VISIBLE
                spinnerDistrict.visibility = View.VISIBLE
                dobTab.text = "";
                dobTab.hint = "अयोग्यता प्रारम्भ की तिथि";
                dobTab.isClickable = true
            }

        })

        buttonVerifyFarmer.setOnClickListener {
            farmerVerificationDtl.RegistrationNo =
                requireActivity().findViewById<TextView>(R.id.textRegNo).text.toString()
            farmerVerificationDtl.MobileNo =
                requireActivity().findViewById<TextView>(R.id.textFMobNo).text.toString()

            val selectedRBEntitlementId: Int = radioGroupEntitlement.checkedRadioButtonId
            if (selectedRBEntitlementId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBEntitlementId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.FarmerEntitlement = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "क्या किसान को उनका सभी क़िस्त प्राप्त हो चूका हैं? 1 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedRBIncomeTaxId: Int = radioGroupIncomeTax.checkedRadioButtonId
            if (selectedRBIncomeTaxId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBIncomeTaxId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.IncomeTaxPayer = selectedRbText
//                if (selectedRbText == "NO")
//                    requireActivity().findViewById<RadioButton>(R.id.radioPhyVerifResponse1).isEnabled =
//                        false
            } else {
                Toast.makeText(
                    requireContext(),
                    "क्या किसान / परिवार  के सदस्य गत वर्ष में  आयकर का भुगतान किए हैं? 2 चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedRBMinisterId: Int = radioGroupMinister.checkedRadioButtonId
            if (selectedRBMinisterId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBMinisterId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.Minister = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "3 a) चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedRBGovtEmpId: Int = radioGroupGovtEmp.checkedRadioButtonId
            if (selectedRBGovtEmpId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBGovtEmpId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.GovtEmp = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "3 b) चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBRetiredEmpId: Int = radioGroupRetired.checkedRadioButtonId
            if (selectedRBRetiredEmpId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBRetiredEmpId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.RetiredEmp = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "3 c) चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBContitutionalId: Int = radioGroupConstitutionalPost.checkedRadioButtonId
            if (selectedRBContitutionalId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBContitutionalId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ConstitutionalPostEmp = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "3 d) चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedRBProfessionalId: Int = radioGroupProfessional.checkedRadioButtonId
            if (selectedRBProfessionalId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBProfessionalId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.ProfessionalEmp = selectedRbText

            } else {
                Toast.makeText(
                    requireContext(),
                    "3 e) चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val selectedRBFamilyGetBenId: Int = radioGroupFamilyGetBen.checkedRadioButtonId
            if (selectedRBFamilyGetBenId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBFamilyGetBenId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.FamilyGetBen = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "3 f) चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedRBLandHaveId: Int = radioGroupLandHave.checkedRadioButtonId
            if (selectedRBLandHaveId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedRBLandHaveId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.LandHave = selectedRbText
            } else {
                Toast.makeText(
                    requireContext(),
                    "3 g) चुने!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            val selectedPhyVerifResponseId: Int = radioGroupPhyVerifResponse.checkedRadioButtonId
            if (selectedPhyVerifResponseId != -1) {
                var selectedRadioButton =
                    requireView().findViewById<RadioButton>(selectedPhyVerifResponseId)
                val selectedRbText: String = selectedRadioButton.text.toString()
                farmerVerificationDtl.PhyVerifResponse = selectedRbText



                if (farmerVerificationDtl.ProfessionalEmp == "YES" || farmerVerificationDtl.ConstitutionalPostEmp == "YES" ||
                    farmerVerificationDtl.RetiredEmp == "YES" || farmerVerificationDtl.GovtEmp == "YES" ||
                    farmerVerificationDtl.Minister == "YES" || farmerVerificationDtl.IncomeTaxPayer == "YES" ||
                    farmerVerificationDtl.FamilyGetBen == "YES"
                ) {
                    if (farmerVerificationDtl.PhyVerifResponse == "Eligible") {
                        Toast.makeText(
                            requireContext(),
                            "उपरोक्त में चयन किये विकल्प के अनुसार 'Eligible' विकल्प मान्य नहीं हैं !",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "भौतिक सत्यापन उपरांत प्रतिक्रिया चुने !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            farmerVerificationDtl.PhyVerifDate =
                requireActivity().findViewById<TextView>(R.id.VerifyDate).text.toString()
            if (farmerVerificationDtl.PhyVerifDate == "") {
                Toast.makeText(
                    requireContext(),
                    "सत्यापन/मृत्यु/अयोग्यता प्रारम्भ की तिथि चुने !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (farmerVerificationDtl.PhyVerifResponse == "Ineligible") {
                val stdDistrict =
                    requireView().findViewById<Spinner>(R.id.spinnerIneligibleReason).selectedItem
                if (stdDistrict == "--Select--") {
                    Toast.makeText(
                        requireContext(),
                        "Please Select Proper Reason of Ineligibility From Dropdown !",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                // your code to perform when the user clicks on the button
                farmerVerificationDtl.PhyVerifReason =
                    districtList.find { it.name == stdDistrict.toString() }!!.id.toString()

            }

            var gps: GPSTracker? = GPSTracker(requireContext())
            if (gps!!.canGetLocation()) {
                farmerVerificationDtl.ActionLat = gps!!.getLatitude()
                farmerVerificationDtl.ActionLong = gps!!.getLongitude()
            } else {
                Toast.makeText(
                    requireContext(),
                    "जीपीएस ऑन करें या जीपीएस जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()
                gps.showSettingsAlert()
                return@setOnClickListener
            }

            farmerVerificationDtl.IMEINo = Settings.Secure.getString(
                requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID
            )
            if (farmerVerificationDtl.IMEINo == "") {
                Toast.makeText(
                    requireContext(),
                    "फोन की जानकारी प्राप्त करने की अनुमति प्रदान करें !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (farmerVerificationDtl.ActionLat == 0.0 || farmerVerificationDtl.ActionLong == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "जीपीएस ऑन करें या जीपीएस जानकारी प्राप्त करने की अनुमति प्रदान करें !",
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
            viewModel.saveApprovedFarmerDetails(farmerVerificationDtl)
        }
        viewModel.liveFarmerVerifyResponse.observe(viewLifecycleOwner, Observer { result ->
            if (result == 1) {
                Toast.makeText(
                    requireContext(),
                    "किसान से सम्बंधित जानकारी सुरक्षित कर ली गई है ।",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(requireView().context, PMKisanListVerifyActivity::class.java)
                // val intent = Intent(requireView().context, FarmerListVerifyActivity::class.java)
                requireView().context.startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(
                    requireContext(),
                    "जानकारी सुरक्षित करने में समस्या हो रही हैं पुनः प्रयास करें !",
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