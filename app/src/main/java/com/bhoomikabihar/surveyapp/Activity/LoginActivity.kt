package com.bhoomikabihar.surveyapp.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bhoomikabihar.surveyapp.Model.LoginResponse
import com.bhoomikabihar.surveyapp.R
import com.example.dbtagri.LocalDataRepository.Dao.LocalDataRepository
import com.example.dbtagri.RemoteDataRepository.ApiClient
import com.example.dbtagri.RemoteDataRepository.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        var localRepository: LocalDataRepository? = null
        localRepository = LocalDataRepository.getInstance(application.applicationContext)
        GlobalScope.launch(Dispatchers.IO) {
            localRepository!!.deleteInputSubsidyData()
        }
        val login = findViewById<Button>(R.id.loginbtn)
        login.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            val loginId = findViewById<EditText>(R.id.loginId).text.trim().toString()
            val txtpassword = findViewById<EditText>(R.id.txtpassword).text.trim().toString()

            if ((loginId == "") && (txtpassword == "")) {
                Toast.makeText(
                    applicationContext, "Enter Proper User ID  and Password !",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                apiClient = ApiClient()
                sessionManager = SessionManager(this)
                sessionManager.clearToken()
                apiClient.getApiService(applicationContext)
                    .login("password", loginId, txtpassword)
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(
                                applicationContext,
                                "Something Wrong !",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                val loginResponse = response.body()
//                                sessionManager.saveAuthToken(loginResponse!!.authToken)
//                                sessionManager.saveAuthDetails(loginResponse)

                                startActivity(mainIntent)

                                finish()
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Enter Proper User ID and Password !",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    })
            }

        }
    }
}


