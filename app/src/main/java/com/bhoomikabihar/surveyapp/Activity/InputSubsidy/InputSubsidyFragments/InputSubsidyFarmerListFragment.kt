package com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhoomikabihar.surveyapp.Activity.InputSubsidy.InputSubsidyFragments.InputSubsidyViewModel
import com.bhoomikabihar.surveyapp.Model.FarmerDetails
import com.bhoomikabihar.surveyapp.R
import com.bseb.crossword.ViewAdaptor.UnApprovedFarmerListAdaptor
import com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyVerifyActivity

import com.example.dbtagri.ViewAdaptor.RecyclerViewClickInterfaceDataCall

class InputSubsidyFarmerListFragment : Fragment(), RecyclerViewClickInterfaceDataCall {

    companion object {
        fun newInstance() = InputSubsidyFarmerListFragment()
    }

    private lateinit var viewModel: InputSubsidyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var root = inflater.inflate(R.layout.fragment_input_subsidy, container, false)

        viewModel = InputSubsidyViewModel(requireActivity().application)


        viewModel.getUnApprovedFarmerList()
        viewModel.liveUnApproveFarmerList.observe(viewLifecycleOwner, Observer { result ->
            if (result !== null) {
                val adapter = UnApprovedFarmerListAdaptor(requireContext(), result, this)
                // Fill Recycler View
                val linearLayoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false
                )
                val recyclerView = root.findViewById(R.id.recyclerViewFarmerList) as RecyclerView
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = adapter
                Toast.makeText(
                    requireContext(),
                    result.count().toString() + " Farmers Details added Successfully.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        });
        return root
    }


    override fun onItemClick(data: FarmerDetails) {

        val intent = Intent(requireView().context, InputSubsidyVerifyActivity::class.java)
        //viewModel.setUnApprovedSelectedFarmer(data)
        intent.putExtra("appId", data.appId)
        intent.putExtra("regId", data.regId)
        intent.putExtra("applicantName", data.applicantName)
        intent.putExtra("father_Husband_Name", data.father_Husband_Name)
        intent.putExtra("mobileNo", data.mobileNo)
        intent.putExtra("totalAffectedRakwa", data.totalAffectedRakwa)
        intent.putExtra("totalSubsidy", data.totalSubsidy)
        intent.putExtra("distcode", data.distcode)
        intent.putExtra("blockCode", data.blockCode)
        intent.putExtra("panchayatCode", data.panchayatCode)
        intent.putExtra("schemeType", data.schemeType)
        startActivity(intent)
    }

}