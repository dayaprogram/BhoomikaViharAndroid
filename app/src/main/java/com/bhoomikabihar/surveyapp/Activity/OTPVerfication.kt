package com.bhoomikabihar.surveyapp.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bseb.crossword.RemoteDataRepository.ApiService
import com.bseb.crossword.RemoteDataRepository.RemoteRepository
import com.bhoomikabihar.surveyapp.CustomClass.GenericTextWatcher
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.RemoteDataRepository.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.*

class OTPVerfication : AppCompatActivity() {
    var countDownTimer: CountDownTimer? = null

    companion object {
        var otp: String = ""
        var Gnerated_otp: String = ""
        var Mobileno = ""
    }


    private lateinit var otp_textbox_one: EditText
    private lateinit var otp_textbox_two: EditText
    private lateinit var otp_textbox_three: EditText
    private lateinit var otp_textbox_four: EditText
    private lateinit var verify_otp: Button

    lateinit var text_seconds: TextView
    lateinit var tv_resend_otp: TextView

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verfication)
        otp_textbox_one = findViewById(R.id.otp_edit_box1)
        otp_textbox_two = findViewById(R.id.otp_edit_box2)
        otp_textbox_three = findViewById(R.id.otp_edit_box3)
        otp_textbox_four = findViewById(R.id.otp_edit_box4)
        verify_otp = findViewById(R.id.verify_otp_btn)
        text_seconds = findViewById(R.id.text_seconds)
        tv_resend_otp = findViewById(R.id.tv_resend_otp)
        val edit = arrayOf(otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four)

        otp_textbox_one.addTextChangedListener(GenericTextWatcher(otp_textbox_one, edit))
        otp_textbox_two.addTextChangedListener(GenericTextWatcher(otp_textbox_two, edit))
        otp_textbox_three.addTextChangedListener(GenericTextWatcher(otp_textbox_three, edit))
        otp_textbox_four.addTextChangedListener(GenericTextWatcher(otp_textbox_four, edit))


        var sessionManager: SessionManager = SessionManager(this)
        OTPVerfication.Mobileno = sessionManager.fetchAuthACDetails().mobileNo
        GETOTP()

        verify_otp.setOnClickListener(View.OnClickListener {
            OTPVerfication.otp =
                otp_textbox_one.text.toString() + otp_textbox_two.text.toString() + otp_textbox_three.text.toString() + otp_textbox_four.text.toString()
            if (OTPVerfication.Gnerated_otp.equals(OTPVerfication.otp)) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please Enter Correct OTP",
                    Toast.LENGTH_SHORT
                )
            }
        })

        tv_resend_otp.setOnClickListener {

            GETOTP()
        }


    }


    fun GETOTP() {
        /*  sessionManager = SessionManager(this)
          sessionManager.clearToken()*/
        val random = Random()
        Gnerated_otp = String.format("%04d", random.nextInt(10000))

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dbtagriculture.bihar.gov.in/")
            .client(RemoteRepository.httpClient.build())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        val api: ApiService = retrofit.create(ApiService::class.java)

        // OTPVerfication.Mobileno = "7979858887"

        Log.e("getOtp", "yes")
        api.sendOtpSMS(OTPVerfication.Mobileno, "AGRDEPTSC", "K$%@#SC", OTPVerfication.Gnerated_otp)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("getOtp", "Error")
                    Toast.makeText(
                        applicationContext,
                        "Error !",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    val result = response.body()
                    if (result == "1") {

                        if (Gnerated_otp != null) {
                            countDownTimer = object : CountDownTimer(30000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    //here you can have your logic to set text to edittext
                                    text_seconds.setVisibility(View.VISIBLE)
                                    tv_resend_otp.setVisibility(View.GONE)
                                    text_seconds.setText("Resend in" + " " + millisUntilFinished / 1000 + " " + "seconds")
                                }

                                override fun onFinish() {
                                    tv_resend_otp.setVisibility(View.VISIBLE)
                                    text_seconds.setVisibility(View.GONE)
                                }
                            }.start()

                            Toast.makeText(
                                applicationContext,
                                "ओ. टी. पी. आपके रजिस्टर्ड मोबाइल नंबर पर भेजा जा रहा है । ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Log.i(
                            "getOtp",
                            "success---> " + OTPVerfication.Gnerated_otp + "    " + result
                        )
                    }
                }
            })


    }
}