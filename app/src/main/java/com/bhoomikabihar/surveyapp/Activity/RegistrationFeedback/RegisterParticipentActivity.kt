package com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.bhoomikabihar.surveyapp.Activity.PdfViewActivity
import com.bhoomikabihar.surveyapp.Model.User
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.RemoteDataRepository.SessionManager
import com.google.android.material.textfield.TextInputEditText


class RegisterParticipentActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterParticipentViewModel

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

        viewModel = RegisterParticipentViewModel(application)

        var inputName = findViewById<TextInputEditText>(R.id.input_name)
//        var inputFatherName = findViewById<TextInputEditText>(R.id.input_father_name)
        var inputMobile = findViewById<TextInputEditText>(R.id.input_mobile)
        var inputEmail = findViewById<TextInputEditText>(R.id.input_email)
        var btnSaveDetail = findViewById<Button>(R.id.btnSaveDetail)
        var radioGroupGender = findViewById<View>(R.id.radioGroupGender) as RadioGroup
        var participantLayout = findViewById<View>(R.id.participantLayout) as ConstraintLayout

//        radioGroupGender.setOnCheckedChangeListener { radioGroup, i ->
//
//            val gen: Int = radioGroup.checkedRadioButtonId
//            if (gen != -1) {
//                var selectedRadioButton = findViewById<RadioButton>(gen)
//                val selectedRbText = selectedRadioButton.text.toString()
//                if (selectedRbText == "MALE") {
//                    participantLayout.setBackgroundColor(R.color.colorLightP)
//                } else if (selectedRbText == "FEMALE") {
//                    participantLayout.setBackgroundColor(R.color.colorLightB)
//                }
//            }
//        }


        var sessionManager = SessionManager(applicationContext)
        var user = sessionManager.fetchAuthACDetails()
        if (user != null) {
            inputName.setText(user.name)
            inputMobile.setText(user.mobileNo)
            inputEmail.setText(user.emailId)

            if (user.gender == "MALE") {
                radioGroupGender.check(R.id.radio1)
            } else if (user.gender == "FEMALE") {
                radioGroupGender.check(R.id.radio2)
            }

        }
        var selectedRbText: String = ""

        btnSaveDetail.setOnClickListener {

            val selectedRBACFamilyBenifyId: Int = radioGroupGender.checkedRadioButtonId
            if (selectedRBACFamilyBenifyId != -1) {
                var selectedRadioButton = findViewById<RadioButton>(selectedRBACFamilyBenifyId)
                selectedRbText = selectedRadioButton.text.toString()

            } else {
                Toast.makeText(
                    this,
                    "select Gender!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            var data = User(
                inputName.text.toString(),
                inputMobile.text.toString(),
                inputEmail.text.toString(),
                selectedRbText,
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

            sessionManager.saveAuthDetails(data)

            val intent =
                Intent(this, PdfViewActivity::class.java)
            var url =
                "https://www.bhoomikavihar.in/MobileApp/RegisterParticipant?Name=" +
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