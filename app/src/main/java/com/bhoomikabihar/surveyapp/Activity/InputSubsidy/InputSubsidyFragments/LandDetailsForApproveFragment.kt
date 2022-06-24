package com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Activity.InputSubsidy.InputSubsidyFragments.FarmerPictureFragment
import com.bhoomikabihar.surveyapp.Activity.InputSubsidy.InputSubsidyFragments.InputSubsidyViewModel
import com.bhoomikabihar.surveyapp.Model.LandDetails
import com.bhoomikabihar.surveyapp.R
import com.bhoomikabihar.surveyapp.ViewAdaptor.LandDetailsApproveListAdaptor
import com.bhoomikabihar.surveyapp.ViewAdaptor.RecyclerViewEditTextInterface

class LandDetailsForApproveFragment : Fragment(), RecyclerViewEditTextInterface {

    var editModelLandList: List<LandDetails>? = null
    private lateinit var viewModel: InputSubsidyViewModel

    companion object {
        fun newInstance() = LandDetailsForApproveFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_land_details_for_approve, container, false)
        viewModel = InputSubsidyViewModel(requireActivity().application)
        var regNo =
            requireActivity().findViewById<TextView>(R.id.textRegNoVerify).text.toString().trim()
        var appNo =
            requireActivity().findViewById<TextView>(R.id.textApplicationId).text.toString().trim()
        var textCalcSubsidy = requireActivity().findViewById<View>(R.id.textCalcSubsidy) as TextView
        var CalcSubsidy = 0.0
        var textCalcLand = requireActivity().findViewById<View>(R.id.textCalcLand) as TextView
        viewModel.getUnApprovedFarmerLand(regNo)

        viewModel.liveUnApproveFarmerLandList.observe(viewLifecycleOwner, Observer { result ->
            editModelLandList = result
            val adapter =
                LandDetailsApproveListAdaptor(requireContext(), editModelLandList!!, this)
            val linearLayoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            val recyclerView =
                root.findViewById(R.id.recyclerViewFarmerLandDetailsList) as RecyclerView
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.isNestedScrollingEnabled = true;
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter

        })
        viewModel.liveCalculetedSubsidyAmt.observe(viewLifecycleOwner, Observer { result ->
            textCalcSubsidy.text = result.toString()
            CalcSubsidy = result
        })
        viewModel.liveCalculetedApprovedLand.observe(viewLifecycleOwner, Observer { result ->
            textCalcLand.text = result.toString()
        })
        val btnClickMe = root.findViewById<Button>(R.id.submitLandDetails)
        btnClickMe.setOnClickListener {


            val unVerifiedLand =
                editModelLandList?.filter { landDetails -> landDetails.approvedRakwa == 0.0 }
            val unVerifiedLandType =
                editModelLandList?.filter { landDetails -> landDetails.approvedLandType == 0 }
            if (unVerifiedLand !== null) {
                if (unVerifiedLand.count() > 0) {
                    Toast.makeText(
                        requireContext(),
                        unVerifiedLand.count().toString() + " Land Rakwa not Verified !",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }

            if (unVerifiedLandType !== null) {
                if (unVerifiedLandType.count() > 0) {
                    Toast.makeText(
                        requireContext(),
                        unVerifiedLandType.count().toString() + " Land Type not Verified !",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }

            val TotalApprovedLand: Double =
                editModelLandList!!.sumByDouble { it.approvedRakwa }
            val TotalAffectedLand = editModelLandList!!.sumByDouble { it.affectedRakwa }
            if (TotalApprovedLand > TotalAffectedLand) {
                Toast.makeText(
                    requireContext(),
                    "Total Approved LAnd Must be Less than or equal to Total Affected Land !",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (textCalcLand.text.toString().toDouble() == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "Land Detail required !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (CalcSubsidy < 1000) {
                Toast.makeText(
                    requireContext(),
                    "Calculated subsidy amount must be greater than or equal to 1000 !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            var totalAmt =
                requireActivity().findViewById<TextView>(R.id.textSubsidyAmt).text.toString();
            if (CalcSubsidy > totalAmt.toDouble()) {
                Toast.makeText(
                    requireContext(),
                    "Calculated subsidy amount must be less than or equal to total subsidy amt !",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            viewModel.setLandDetails(
                textCalcLand.text.toString().toDouble(),
                textCalcSubsidy.text.toString().toDouble(),
                regNo,
                appNo
            )
            val fragment: Fragment = FarmerPictureFragment.newInstance()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return root
    }


    override fun onTextChanged(position: Int, text: String) {
        if (text != "" && text != null) {
            if (text.toDouble() > editModelLandList!![position].affectedRakwa) {
                editModelLandList!![position].approvedRakwa = 0.0
                viewModel.setActionOnLand(editModelLandList!![position])
                Toast.makeText(
                    requireContext(),
                    "Approved Land Must be less than or equal to Affected Land !",
                    Toast.LENGTH_SHORT
                ).show()
                return
            } else {
                if (editModelLandList != null) {
                    if (!editModelLandList!!.isEmpty()) {
                        editModelLandList!![position].approvedRakwa = text.toDouble()
                        viewModel.setActionOnLand(editModelLandList!![position])
                    }
                }
            }
        }
    }

    override fun onItemSelected(position: Int, text: String) {
        if (text == "") {
            Toast.makeText(
                requireContext(),
                "Please Select Land Type !",
                Toast.LENGTH_SHORT
            ).show()
            return
        } else {
            if (editModelLandList != null) {
                if (!editModelLandList!!.isEmpty()) {
                    var landTypeAprved: Int = 0
                    if (text == "सिंचित") {
                        landTypeAprved = 1
                    } else if (text == "वर्षा आश्रित/असिंचित") {
                        landTypeAprved = 2
                    }
                    editModelLandList!![position].approvedLandType = landTypeAprved
                    viewModel.setActionOnLand(editModelLandList!![position])
                }
            }
        }
    }

}