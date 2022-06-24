package com.bhoomikabihar.surveyapp.Activity.PMKISANAnkshan

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.bhoomikabihar.surveyapp.Activity.MainActivity
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.PMKISANAnkshenViewModel
import com.bhoomikabihar.surveyapp.Model.PMKISANAnkshenModel
import com.bhoomikabihar.surveyapp.R
import java.util.*

class PMKISANAnksheanActivity : AppCompatActivity() {

    var toolbar_godown: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pmkisanankshean)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        var viewModel = PMKISANAnkshenViewModel(application)
        viewModel.GetCountPMKISANAnkshen();

        var TotalRevVill = findViewById<EditText>(R.id.TotalRevVill)
        var TotalBenifCompleted = findViewById<EditText>(R.id.TotalBenifCompleted)
        var TotalIneliglibleFarmer = findViewById<EditText>(R.id.TotalIneliglibleFarmer)
        var TotalEligibleBenifLeft = findViewById<EditText>(R.id.TotalEligibleBenifLeft)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var toolbar_pmkisanankshan = findViewById<Toolbar>(R.id.toolbar_pmkisanankshan)
            toolbar_pmkisanankshan = findViewById(R.id.toolbar_pmkisanankshan)
            toolbar_pmkisanankshan.setTitle(resources.getString(R.string.app_name))
            toolbar_pmkisanankshan.setSubtitle(resources.getString(R.string.pm_kisan_ankshan))
            toolbar_pmkisanankshan.setSubtitleTextColor(resources.getColor(R.color.white))
            toolbar_pmkisanankshan.setTitleTextColor(resources.getColor(R.color.white))
            setSupportActionBar(toolbar_pmkisanankshan)
            Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            toolbar_pmkisanankshan.setNavigationOnClickListener(View.OnClickListener { finish() })
        }


        var TotalVillCount = findViewById<TextView>(R.id.TotalVillCount)
        var TotalBenifCount = findViewById<TextView>(R.id.TotalBenifCount)

        viewModel.livepmkisanAnkshanDataCount.observe(this, Observer { result ->
            TotalVillCount.text = "कुल राजस्व ग्राम : " + result.TotalVillCount
            TotalBenifCount.text = "कुल लाभुकों की संख्या : " + result.TotalBenifCount
            TotalRevVill.setText(result.RevVillCovered.toString());
            TotalBenifCompleted.setText(result.BenifAuditCompleted.toString());
            TotalIneliglibleFarmer.setText(result.IneliglibleBenif.toString());
            TotalEligibleBenifLeft.setText(result.EligibleNonBenif.toString());

        })

        var buttonVerifyFarmer = findViewById<View>(R.id.buttonVerifySave) as Button
        var pmkisanAnkshanDtl = PMKISANAnkshenModel();

        buttonVerifyFarmer.setOnClickListener {
            pmkisanAnkshanDtl.RevVillCovered = TotalRevVill.text.toString().trim().toInt();
            if (pmkisanAnkshanDtl.RevVillCovered == null) {
                Toast.makeText(
                    applicationContext, "Enter Proper data!",
                    Toast.LENGTH_LONG
                ).show()
            }

            pmkisanAnkshanDtl.BenifAuditCompleted =
                TotalBenifCompleted.text.toString().trim().toInt();
            if (pmkisanAnkshanDtl.BenifAuditCompleted == null) {
                Toast.makeText(
                    applicationContext, "Enter Proper data!",
                    Toast.LENGTH_LONG
                ).show()
            }

            pmkisanAnkshanDtl.IneliglibleBenif =
                TotalIneliglibleFarmer.text.toString().trim().toInt();
            if (pmkisanAnkshanDtl.IneliglibleBenif == null) {
                Toast.makeText(
                    applicationContext, "Enter Proper data!",
                    Toast.LENGTH_LONG
                ).show()
            }

            pmkisanAnkshanDtl.EligibleNonBenif =
                TotalEligibleBenifLeft.text.toString().trim().toInt();

            if (pmkisanAnkshanDtl.EligibleNonBenif == null) {
                Toast.makeText(
                    applicationContext, "Enter Proper data!",
                    Toast.LENGTH_LONG
                ).show()
            }

            viewModel.savePMKISANAnkshenDetails(pmkisanAnkshanDtl)
        }
        viewModel.liveFarmerVerifyResponse.observe(this, Observer { result ->
            if (result == 1) {
                Toast.makeText(
                    applicationContext,
                    "जानकारी सुरक्षित कर ली गई है ।",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(application, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "जानकारी सुरक्षित करने में समस्या हो रही हैं पुनः प्रयास करें !",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}