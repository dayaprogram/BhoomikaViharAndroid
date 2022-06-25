package com.bhoomikabihar.surveyapp.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bhoomikabihar.surveyapp.R


class PdfViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)


        val toolbar: Toolbar = findViewById(R.id.title_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val webView = findViewById<WebView>(R.id.webViewActivity)
        webView!!.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        val name = intent.getStringExtra("filePath")

        val filename = "http://164.100.130.206/Document/FloodDocument/$name"
        //val filename = "http://164.100.130.206/Document/FloodDocument/2071095567288KF.pdf"
        var url = "http://docs.google.com/gview?embedded=true&url=$filename"
        url = intent.getStringExtra("URL").toString()
        webView.loadUrl(url)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                progressbar.visibility = View.GONE
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}