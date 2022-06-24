package com.bhoomikabihar.surveyapp.Activity.InputSubsidy.InputSubsidyFragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyActivity
import com.bhoomikabihar.surveyapp.R

class NeighbourFarmerDetailsFragment : Fragment() {
    companion object {
        fun newInstance() = NeighbourFarmerDetailsFragment()
    }

    private lateinit var viewModel: InputSubsidyViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_neighbour_farmer_details, container, false)
        viewModel = InputSubsidyViewModel(requireActivity().application)
        var regNo =
            requireActivity().findViewById<TextView>(R.id.textRegNoVerify).text.toString().trim()
        var appNo =
            requireActivity().findViewById<TextView>(R.id.textApplicationId).text.toString().trim()
        val btn_click_me = root.findViewById<Button>(R.id.submitNibourDetails)
        // set on-click listener
        btn_click_me.setOnClickListener {
            val farmerName1 =
                root.findViewById<EditText>(R.id.editTextTextPersonName1).text.toString()
            val farmerMobile1 = root.findViewById<EditText>(R.id.editTextPhone1).text.toString()
            val farmerName2 =
                root.findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
            val farmerMobile2 = root.findViewById<EditText>(R.id.editTextPhone2).text.toString()

            if (farmerName1 == "") {
                Toast.makeText(
                    requireContext(), "First Farmer Name Required !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (farmerMobile1 == "") {
                Toast.makeText(
                    requireContext(), "First Farmer Mobile No Required !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                if (farmerMobile1.length != 10) {
                    Toast.makeText(
                        requireContext(), "First Farmer Mobile No should be 10 digit only !",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }
            if (farmerName2 == "") {
                Toast.makeText(
                    requireContext(), "Second Farmer Name Required !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (farmerMobile2 == "") {
                Toast.makeText(
                    requireContext(), "First Farmer Mobile No Required !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                if (farmerMobile2.length != 10) {
                    Toast.makeText(
                        requireContext(), "First Farmer Mobile No should be 10 digit only !",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }

            if (farmerMobile2.contentEquals(farmerMobile1)) {
                Toast.makeText(
                    requireContext(),
                    "First Farmer Mobile No. Should Not be  same  as Second Farmer Mobile No. !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }



            viewModel.setNighbourFarmerDetails(
                farmerName1,
                farmerMobile1,
                farmerName2,
                farmerMobile2,

                regNo, appNo
            );
            viewModel.getInputSubsidyApplicationStatus(regNo, appNo)
            viewModel.liveApplicationStutus.observe(viewLifecycleOwner, Observer { result ->
                if (result == 1) {
                    Toast.makeText(
                        requireContext(), "Data Saved Successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(requireView().context, InputSubsidyActivity::class.java)
                    requireView().context.startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(
                        requireContext(), "Some Issue ! Try Again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
        return root
    }


}