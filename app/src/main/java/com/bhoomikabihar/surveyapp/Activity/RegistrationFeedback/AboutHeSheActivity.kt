package com.bhoomikabihar.surveyapp.Activity.RegistrationFeedback

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.bhoomikavihar.surveyapp.R

class AboutHeSheActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_he_she)

        var participateBtn=findViewById<Button>(R.id.ParticipateNow)

        val toolbar: Toolbar = findViewById(R.id.title_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val webView = findViewById<WebView>(R.id.webViewActivity)
        webView!!.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        //val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh);
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

      //  val url = intent.getStringExtra("URL")!!
        var url = "https://www.bhoomikavihar.in/HomeAndroid/AboutHeShe"
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                progressbar.visibility = View.GONE
            }
        }

        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            )
        }
        participateBtn.setOnClickListener{

            val intent = Intent(this, RegisterParticipentActivity::class.java)
             startActivity(intent)
            finish()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}