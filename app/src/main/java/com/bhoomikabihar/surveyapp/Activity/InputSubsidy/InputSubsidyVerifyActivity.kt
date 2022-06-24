package com.dbtagri.dbtagriverify.Activity.InputSubsidy

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bhoomikabihar.surveyapp.Activity.InputSubsidy.InputSubsidyFragments.InputSubsidyViewModel
import com.bhoomikabihar.surveyapp.Model.FarmerDetails
import com.bhoomikabihar.surveyapp.R
import com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyFragments.FarmerDetailsForAprvFragment


private lateinit var viewModel: InputSubsidyViewModel

class InputSubsidyVerifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_subsidy_verify)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        viewModel = InputSubsidyViewModel(application)

        var textRegNo = findViewById<View>(R.id.textRegNoVerify) as TextView
        var textApplicationId = findViewById<View>(R.id.textApplicationId) as TextView
        var textFName = findViewById<View>(R.id.textFName) as TextView
        var textFMobNo = findViewById<View>(R.id.textFMobNo) as TextView
        var textFFatherName = findViewById<View>(R.id.textFFatherName) as TextView
        var textAffectedLand = findViewById<View>(R.id.textAffectedLand) as TextView
        var textSubsidyAmt = findViewById<View>(R.id.textSubsidyAmt) as TextView


        viewModel.setUnApprovedSelectedFarmer(
            FarmerDetails(
                intent.getStringExtra("regId")!!,
                intent.getStringExtra("appId")!!,
                intent.getStringExtra("applicantName")!!,
                intent.getStringExtra("father_Husband_Name")!!,
                intent.getDoubleExtra("totalAffectedRakwa", 0.0),
                intent.getDoubleExtra("totalSubsidy", 0.0),
                intent.getStringExtra("mobileNo")!!,
                intent.getIntExtra("schemeType", 0),
                intent.getIntExtra("distcode", 0),
                intent.getIntExtra("blockCode", 0),
                intent.getStringExtra("panchayatCode")!!
            )
        )

        viewModel.liveUnApproveSelectedFarmer.observe(this, Observer { result ->
            textRegNo.text = result.regId
            textApplicationId.text = result.appId
            textFName.text = result.applicantName
            textFMobNo.text = result.mobileNo
            textFFatherName.text = result.father_Husband_Name
            textAffectedLand.text = result.totalAffectedRakwa.toString()
            textSubsidyAmt.text = result.totalSubsidy.toString()
            //  textCalcSubsidy.text = "0.0"
        })



        if (savedInstanceState == null) {
            var argumentFragment = FarmerDetailsForAprvFragment.newInstance()
            var data: Bundle = Bundle();//Use bundle to pass data
            data.putString("regId", intent.getStringExtra("regId"));
            data.putString("appId", intent.getStringExtra("appId")!!);
            data.putInt("distcode", intent.getIntExtra("distcode", 0)!!);
            data.putInt("blockCode", intent.getIntExtra("blockCode", 0)!!);
            data.putString("panchayatCode", intent.getStringExtra("panchayatCode")!!);
            argumentFragment.arguments = data;//Finally set argument bundle to fragment

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, argumentFragment)
                .commitNow()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}