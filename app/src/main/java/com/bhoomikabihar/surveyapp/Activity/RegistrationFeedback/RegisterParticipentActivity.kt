package com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.SocialAuditVerificationViewModel
import com.bhoomikabihar.surveyapp.R


class RegisterParticipentActivity : AppCompatActivity() {

    private lateinit var viewModel: SocialAuditVerificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_participent)
        val toolbar: Toolbar = findViewById(R.id.title_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        //supportActionBar?.setDisplayShowTitleEnabled(true);

        onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                onBackPressed()

            }
        })

        viewModel = SocialAuditVerificationViewModel(application)

//        var textRegNo = findViewById<View>(R.id.textRegNo) as TextView


    }
}