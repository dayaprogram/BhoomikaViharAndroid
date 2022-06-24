package com.bhoomikabihar.surveyapp.Activity.PMKisanVerification

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bhoomikabihar.surveyapp.Activity.VerifyFarmer.ui.VerificationFragments.SocialAuditVerificationFragment
import com.bhoomikabihar.surveyapp.Model.FarmerRegDetails
import com.bhoomikabihar.surveyapp.R


class SocialAuditVerificationActivity : AppCompatActivity() {
    private lateinit var viewModel: SocialAuditVerificationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.farmer_verification_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        viewModel = SocialAuditVerificationViewModel(application)

        var textRegNo = findViewById<View>(R.id.textRegNo) as TextView
        var textAadharNo = findViewById<View>(R.id.textAadharNo) as TextView
        var textFName = findViewById<View>(R.id.textFName) as TextView
        var textFMobNo = findViewById<View>(R.id.textFMobNo) as TextView
        var textFFatherName = findViewById<View>(R.id.textFFatherName) as TextView
        var textDob = findViewById<View>(R.id.textDob) as TextView
        var textGender = findViewById<View>(R.id.textGender) as TextView
        var textVill = findViewById<View>(R.id.textVill) as TextView
        var textCatogery = findViewById<View>(R.id.textCatogery) as TextView
        var textFarmerGrade = findViewById<View>(R.id.textFarmerGrade) as TextView

        viewModel.setUnVerifiedSelectedFarmer(
            FarmerRegDetails(
                intent.getStringExtra("Registration_ID")!!,
                intent.getStringExtra("AadhaarNumber")!!,
                intent.getStringExtra("Farmer_FName")!!,
                intent.getStringExtra("Farmer_LName")!!,
                intent.getStringExtra("Father_Husband_Name")!!,
                intent.getStringExtra("Gender")!!,
                intent.getStringExtra("DOB")!!,
                intent.getStringExtra("CastCateogary")!!,
                intent.getStringExtra("VillageCode")!!,
                intent.getStringExtra("VillName") ?: "",
                intent.getStringExtra("MobileNumber")!!,
                intent.getStringExtra("FarmerGrade") ?: "", 0
            )
        )
        viewModel.liveUnApproveSelectedFarmer.observe(this, Observer { result ->
            textRegNo.text = result.Registration_ID
            textAadharNo.text = result.AadhaarNumber
            textFName.text = result.Farmer_FName + " " + (result.Farmer_LName!!.trim() ?: "")
            textFMobNo.text = result.MobileNumber
            textFFatherName.text = result.Father_Husband_Name
            textDob.text = result.DOB
            textGender.text = result.Gender
            textVill.text = result.VillName
            textCatogery.text = result.CastCateogary
            textFarmerGrade.text = result.FarmerGrade
        })


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SocialAuditVerificationFragment.newInstance())
                .commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}