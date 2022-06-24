package com.bhoomikabihar.surveyapp.Activity.VerifyFarmer.ui.VerificationFragments

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bhoomikabihar.surveyapp.Activity.PMKisanRecovery.PMKisanListRecoveryActivity
import com.bhoomikabihar.surveyapp.Activity.PMKisanRecovery.PMKisanRecoveryViewModel
import com.bhoomikabihar.surveyapp.Comman.GPSTracker
import com.bhoomikabihar.surveyapp.Model.PMKisanRecovery
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.RemoteDataRepository.SessionManager
import java.text.SimpleDateFormat
import java.util.*


class PMKisanRecoveryFragment : Fragment() {
    private lateinit var viewModel: PMKisanRecoveryViewModel


    companion object {
        fun newInstance() = PMKisanRecoveryFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pmkisan_recovery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = PMKisanRecoveryViewModel(requireActivity().application)
        var sessionManager: SessionManager = SessionManager(requireActivity().applicationContext)

        val Installmenttext =
            requireView().findViewById(R.id.txtNoOfInstallment) as TextView //Find textview Id

        val getArgumentInstallments = requireArguments().getInt("NoOfInstallments")

        Installmenttext.text =
            "Total No Of Installment-$getArgumentInstallments and Recovery Amount-" + (getArgumentInstallments * 2000)
        val tranDateLable = requireView().findViewById(R.id.TextTranDate) as TextView
        val tranDate = requireView().findViewById(R.id.editTextTranDate) as TextView
        tranDateLable.setOnClickListener(View.OnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)
            // date picker dialog
            var datepicker = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth -> tranDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) },
                year,
                month,
                day
            )
            datepicker.show()
        })


        var textAcDeclearation =
            requireView().findViewById<CheckBox>(R.id.textAcDeclearation)
        var textAcDeclearation2 =
            requireView().findViewById<CheckBox>(R.id.textAcDeclearation2)
        var buttonVerifyFarmer =
            requireView().findViewById<View>(R.id.buttonVerifySave) as Button
        var farmerVerificationDtl = PMKisanRecovery()
        textAcDeclearation.text =
            "मैं कृषि " + sessionManager.fetchAuthACDetails().userRole + " " + sessionManager.fetchAuthACDetails().name.capitalize() +
                    " घोषणा करता / करती हूँ कि मेरे द्वारा PM-किसान लाभुक  की दी गई उपरोक्त जानकारी सही है" +
                    " एवं मैंने लाभुक के उपरोक्त जानकारी  को सत्यापित किया है ।"
        textAcDeclearation2.text =
            "मैं कृषि " + sessionManager.fetchAuthACDetails().userRole + " " + sessionManager.fetchAuthACDetails().name.capitalize() +
                    " घोषणा करता / करती हूँ कि मेरे द्वारा PM-किसान लाभुक  की दी गई उपरोक्त जानकारी सही है" +
                    " एवं मैंने लाभुक के उपरोक्त जानकारी  को सत्यापित किया है ।"
        val transactionMethods = resources.getStringArray(R.array.transaction_method)
        var spinnerTransactionMethod = requireView().findViewById<Spinner>(R.id.spinnerTranMethod)

        // Creating adapter for spinner
        var dataAdapterTransactionMethod =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, transactionMethods)
        // Drop down layout style - list view with radio button
        dataAdapterTransactionMethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        spinnerTransactionMethod.adapter = dataAdapterTransactionMethod
        spinnerTransactionMethod.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {


                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        buttonVerifyFarmer.setOnClickListener {
            val parser = SimpleDateFormat("dd-MM-YYYY")
            val formatter = SimpleDateFormat("yyyy-MM-dd")


            farmerVerificationDtl.RegistrationNo =
                requireActivity().findViewById<TextView>(R.id.textRegNo).text.toString()
            farmerVerificationDtl.MobileNo =
                requireActivity().findViewById<TextView>(R.id.textFMobNo).text.toString()
            farmerVerificationDtl.RefundTrnRefNO =
                requireActivity().findViewById<EditText>(R.id.editTextTranRefNO).text.toString()
            if (farmerVerificationDtl.RefundTrnRefNO == "") {
                Toast.makeText(
                    requireContext(),
                    "Transaction No. required !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var refundAmt =
                requireActivity().findViewById<EditText>(R.id.editTextTranAmt).text.toString()
            if (refundAmt == "") {
                Toast.makeText(
                    requireContext(),
                    "Refund Amount required !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                farmerVerificationDtl.RefundAmt =
                    refundAmt.toDouble()
            }
            //  farmerVerificationDtl.RefundTrnDate =
            var tranDate =
                requireActivity().findViewById<TextView>(R.id.editTextTranDate).text.toString()
            if (tranDate == "") {
                Toast.makeText(
                    requireContext(),
                    "Transaction Date required !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            var pDate = formatter.format(parser.parse(tranDate))
            farmerVerificationDtl.RefundTrnDate = pDate

            farmerVerificationDtl.RefundMethod =
                requireActivity().findViewById<Spinner>(R.id.spinnerTranMethod).selectedItem.toString()
                    .trim()
            if (farmerVerificationDtl.RefundMethod == "SELECT") {
                Toast.makeText(
                    requireContext(),
                    "Select Transaction Method !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (!textAcDeclearation.isChecked) {
                Toast.makeText(
                    requireContext(),
                    "कृषि समन्वयक घोषणा को चुनें  !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (!textAcDeclearation2.isChecked) {
                Toast.makeText(
                    requireContext(),
                    "कृषि समन्वयक घोषणा को चुनें  !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
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
                val telephonyManager =
                    requireActivity().getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
                var IMEINumber: String =
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//                        // telephonyManager.imei
////                        import android.provider.Settings.Secure;
//                        Settings.Secure.getString(
//                            requireContext().getContentResolver(),
//                            Settings.Secure.ANDROID_ID
//                        );
//                    } else
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        // telephonyManager.imei
//                        import android.provider.Settings.Secure;
                        Settings.Secure.getString(
                            requireContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID
                        );

                    } else {
                        telephonyManager.getDeviceId();
                        telephonyManager.line1Number
                    }
                farmerVerificationDtl.IMEINo = IMEINumber
            }

//            if (farmerVerificationDtl.IMEINo == "") {
//                Toast.makeText(
//                    requireContext(),
//                    "फोन की जानकारी प्राप्त करने की अनुमति प्रदान करें !",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@setOnClickListener
//            }
//            if (farmerVerificationDtl.ActionLat == 0.0 || farmerVerificationDtl.ActionLong == 0.0) {
//                Toast.makeText(
//                    requireContext(),
//                    "जीपीएस ऑन करें या जीपीएस जानकारी प्राप्त करने की अनुमति प्रदान करें !",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@setOnClickListener
//            }


            viewModel.saveRecoveredFarmerDetails(farmerVerificationDtl)
        }
        viewModel.liveFarmerVerifyResponse.observe(viewLifecycleOwner, Observer { result ->
            if (result == 1) {
                Toast.makeText(
                    requireContext(),
                    "किसान से सम्बंधित जानकारी सुरक्षित कर ली गई है । ",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(requireView().context, PMKisanListRecoveryActivity::class.java)
                // val intent = Intent(requireView().context, FarmerListVerifyActivity::class.java)
                requireView().context.startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(
                    requireContext(),
                    "कोई समस्या है !  पुनः प्रयास करें !",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }


    var REQUEST_READ_PHONE_STATE: Int = 1;
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