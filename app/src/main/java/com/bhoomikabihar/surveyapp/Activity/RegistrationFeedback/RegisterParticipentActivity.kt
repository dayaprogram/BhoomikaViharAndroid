package com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bhoomikabihar.surveyapp.Activity.PMKisanVerification.SocialAuditVerificationViewModel
import com.bhoomikabihar.surveyapp.Activity.PdfViewActivity
import com.bhoomikabihar.surveyapp.Model.LoginResponse
import com.bhoomikabihar.surveyapp.Model.User
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.RemoteDataRepository.SessionManager
import com.google.android.material.textfield.TextInputEditText


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

//        onBackPressedDispatcher.addCallback(this, object :
//            OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                onBackPressed()
//            }
//        })

        viewModel = SocialAuditVerificationViewModel(application)

        var inputName = findViewById<TextInputEditText>(R.id.input_name)
//        var inputFatherName = findViewById<TextInputEditText>(R.id.input_father_name)
        var inputMobile = findViewById<TextInputEditText>(R.id.input_mobile)
        var inputEmail = findViewById<TextInputEditText>(R.id.input_email)
        var btnSaveDetail = findViewById<Button>(R.id.btnSaveDetail)

        btnSaveDetail.setOnClickListener {
            var data = User(
                inputName.text.toString(),
                inputMobile.text.toString(),
                inputEmail.text.toString(),
                "",
                ""
            )
            if (data.name == "") {
                Toast.makeText(
                    applicationContext,
                    "Please Enter Proper Name !",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (data.mobileNo == "" && data.mobileNo.length == 10) {
                Toast.makeText(
                    applicationContext,
                    "Please Enter Proper  Mobile !",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (data.emailId == "") {
                Toast.makeText(
                    applicationContext,
                    "Please Enter Proper  Email !",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            var sessionManager = SessionManager(applicationContext)
            sessionManager.saveAuthDetails(data)

            val intent =
                Intent(this, PdfViewActivity::class.java)
            var url =
                "https://www.bhoomikavihar.in/MobileApp/PersonalInfo?Name=" +
                        data.name + "&MobileNo=" + data.mobileNo +
                        "&EmailId=" + data.emailId
            intent.putExtra("URL", url)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}