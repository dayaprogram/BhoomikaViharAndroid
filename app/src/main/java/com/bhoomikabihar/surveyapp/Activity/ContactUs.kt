package com.bhoomikabihar.surveyapp.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bhoomikabihar.surveyapp.R

class ContactUs : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
//        val toolbar: Toolbar = findViewById(R.id.title_toolbar)
//
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true);
//        supportActionBar?.setDisplayShowHomeEnabled(true);
//
//
//        val textmobile = findViewById<TextView>(R.id.textmobile)
//        // set on-click listener
//        textmobile.setOnClickListener {
//            val i = Intent(Intent.ACTION_CALL)
//            i.data = Uri.parse("tel:" + textmobile.text.toString())
//            if (ContextCompat.checkSelfPermission(
//                    applicationContext,
//                    Manifest.permission.CALL_PHONE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                startActivity(i)
//            } else {
//                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
//            }
//        }
//        val textemail = findViewById<TextView>(R.id.textemail)
//        // set on-click listener
//        textemail.setOnClickListener {
////            val intent = Intent(Intent.ACTION_VIEW)
////            val data = Uri.parse(
////                "mailto:" + textemail.text + "?subject=" + Uri.encode("") + "&body=" + Uri.encode("")
////            )
////            intent.data = data
////            startActivity(intent)
//        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}