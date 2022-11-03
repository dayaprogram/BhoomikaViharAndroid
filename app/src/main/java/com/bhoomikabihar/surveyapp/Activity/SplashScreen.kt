package com.bhoomikabihar.surveyapp.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bhoomikavihar.surveyapp.R
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class SplashScreen : AppCompatActivity() {
    private var appupdate: AppUpdateManager? = null
    private val REQUEST_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

//        appupdate = AppUpdateManagerFactory.create(this)
//        checkUpdate()


        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            redirectToIntentAfterSplashScreen()
        }, 2000)
    }


    private fun redirectToIntentAfterSplashScreen() {
       // var sessionManager: SessionManager = SessionManager(this)
       // val authUser = sessionManager.fetchAuthACDetails()
        //if (authUser.userRole == "AC" || authUser.userRole == "CO" || authUser.userRole == "DAO" || authUser.userRole == "ADM(Revenue)") {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
//        } else {
//            val mainIntent = Intent(this, LoginActivity::class.java)
//            startActivity(mainIntent)
//            finish()
//        }

    }


    fun checkUpdate() {
        appupdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->

            if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request an immediate update.
                appupdate?.startUpdateFlowForResult(
                    updateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inProgressUpdate()

    }

    fun inProgressUpdate() {
        appupdate?.appUpdateInfo?.addOnSuccessListener { updateInfo ->

            if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request an immediate update.
                appupdate?.startUpdateFlowForResult(
                    updateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }


}
