package com.bhoomikabihar.surveyapp.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bhoomikabihar.surveyapp.Model.ViewModels.CommanViewModel
import com.bhoomikabihar.surveyapp.R

class SyncApp : AppCompatActivity() {
    lateinit var viewModel: CommanViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_app)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        viewModel = CommanViewModel(application)

        var buttonSync = findViewById<Button>(R.id.buttonSync)
        var buttonUpload = findViewById<Button>(R.id.buttonUpload)
        var textLocalAprvdCount = findViewById<TextView>(R.id.textLocalAprvdCount)
        var textLocalAprvPendingCount = findViewById<TextView>(R.id.textLocalAprvPendingCount)
        var textLocalRejectPendingCount = findViewById<TextView>(R.id.textLocalRejectPendingCount)

        var spinner = findViewById<View>(R.id.syncProgressBar) as ProgressBar
        buttonUpload.isEnabled = false
        buttonSync.setOnClickListener {
            spinner.visibility = View.VISIBLE;
            viewModel.getUnApprovedFarmerListSync()
            viewModel.getApprovedFarmerCount()
            viewModel.getUnApprovedFarmerCount()
        }

        buttonUpload.setOnClickListener {
            buttonUpload.isEnabled = false
            viewModel.saveInputSubsidyApprovedFarmerDetailsUpload()
        }
        viewModel.getApprovedFarmerCount()
        viewModel.getUnApprovedFarmerCount()
        viewModel.getRejectedFarmerCount()
        viewModel.getInputSubUnUploadCount()
        viewModel.liveUnUploaedFarmerCount.observe(this, Observer { result ->
            if (result > 0) {
                buttonSync.isEnabled = false
            }
        })
        viewModel.liveApprovedFarmerCount.observe(this, Observer { result ->
            if (result > 0) {
                buttonUpload.isEnabled = true
            }
            textLocalAprvdCount.text = result.toString()
        })
        viewModel.liveRejectFarmerCount.observe(this, Observer { result ->
            if (result > 0) {
                buttonUpload.isEnabled = true
            }
            textLocalRejectPendingCount.text = result.toString()
        })
        viewModel.liveUnApprovedFarmerCount.observe(this, Observer { result ->
            textLocalAprvPendingCount.text = result.toString()
        })
        viewModel.liveSyncProcessStatus.observe(this, Observer { result ->
            if (result == 1) {
                spinner.visibility = View.GONE
            }
        })

        viewModel.liveFarmerVerifyResponse.observe(this, Observer { result ->
            viewModel.getApprovedFarmerCount()
            viewModel.getUnApprovedFarmerCount()
            viewModel.getRejectedFarmerCount()
            viewModel.getInputSubUnUploadCount()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}