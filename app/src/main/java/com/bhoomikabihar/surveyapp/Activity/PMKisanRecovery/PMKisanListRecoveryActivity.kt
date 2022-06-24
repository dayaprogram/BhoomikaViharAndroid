package com.bhoomikabihar.surveyapp.Activity.PMKisanRecovery

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bseb.crossword.ViewAdaptor.UnVerifiedFarmerListAdaptor
import com.bhoomikabihar.surveyapp.Model.FarmerRegDetails
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.ViewAdaptor.RecyclerViewClickInterfaceVerifyFarmer

class PMKisanListRecoveryActivity : AppCompatActivity(), RecyclerViewClickInterfaceVerifyFarmer {
    private var spinner: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_list_verify)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        var viewModel: PMKisanRecoveryViewModel = PMKisanRecoveryViewModel(application)

        var searchButton = findViewById<View>(R.id.searchFarmer) as SearchView

        spinner = findViewById<View>(R.id.progressBar) as ProgressBar
        spinner!!.visibility = View.VISIBLE;

        viewModel.getUnRecoveredPMKisanList()
        viewModel.liveUnVerifiedFarmerList.observe(this, Observer { result ->
            spinner!!.visibility = View.GONE;
            if (result !== null) {
                if (result.isEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        "सत्यापन के लिए किसान सूचि उपलब्ध नहीं है !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val adapter = UnVerifiedFarmerListAdaptor(application, result, this)
                // Fill Recycler View

                searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null) {
                            if (newText.length == 10) {
                                viewModel.getUnRecoveredFarmerByMobileNo(newText)
                            } else {
                                adapter.filter.filter(newText)
                                return false
                            }
                        }
                        return false
                    }
                })

                searchButton.setOnCloseListener {
                    viewModel.getUnRecoveredPMKisanList()
                    adapter.filter.filter("")
                    false
                }
                val linearLayoutManager = LinearLayoutManager(
                    applicationContext, LinearLayoutManager.VERTICAL, false
                )
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFarmerVerifyList)
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = adapter
                Toast.makeText(
                    this,
                    result.count().toString() + " किसान सत्यापन के लिए उपलब्ध है ।",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "सत्यापन के लिए किसान सूचि उपलब्ध नहीं है !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        });

        viewModel.liveUnVerifiedFarmer.observe(this, Observer { result ->
            if (result !== null) {
                val adapter = UnVerifiedFarmerListAdaptor(application, result, this)
                // Fill Recycler View
                val linearLayoutManager = LinearLayoutManager(
                    applicationContext, LinearLayoutManager.VERTICAL, false
                )
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFarmerVerifyList)
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(
                    applicationContext,
                    "सत्यापन के लिए किसान सूचि उपलब्ध नहीं है !",
                    Toast.LENGTH_SHORT
                ).show()
            }
        });
    }

    override fun onItemClick(data: FarmerRegDetails) {
        val intent = Intent(applicationContext, PMKisanRecoveryActivity::class.java)
        intent.putExtra("Registration_ID", data.Registration_ID)
        intent.putExtra("AadhaarNumber", data.AadhaarNumber)
        intent.putExtra("Farmer_FName", data.Farmer_FName)
        intent.putExtra("Farmer_LName", data.Farmer_LName)
        intent.putExtra("Father_Husband_Name", data.Father_Husband_Name)
        intent.putExtra("Gender", data.Gender)
        intent.putExtra("DOB", data.DOB)
        intent.putExtra("CastCateogary", data.CastCateogary)
        intent.putExtra("VillageCode", data.VillageCode)
        intent.putExtra("VillName", data.VillName)
        intent.putExtra("MobileNumber", data.MobileNumber)
        intent.putExtra("FarmerGrade", data.FarmerGrade)
        intent.putExtra("NoOfInstallments", data.NoOfInstallments)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}