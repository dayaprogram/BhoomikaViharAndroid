package com.bhoomikabihar.surveyapp.Activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
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

        webView.settings.javaScriptEnabled = true
        // Enable responsive layout
        webView.getSettings().setUseWideViewPort(true);
// Zoom out if the content width is greater than the width of the viewport
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false); // allow pinch to zooom
        webView.getSettings()
            .setDisplayZoomControls(false); // disable the default zoom controls on the page
        var url = intent.getStringExtra("URL").toString()
        webView.loadUrl(url)

//        webView.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView, url: String) {
//                progressbar.visibility = View.GONE
//            }
//
//
//
//        }

        webView.webViewClient = object : WebViewClient() {
            // var progressDialog: ProgressDialog? = ProgressDialog(Context)
            val progressbar = findViewById<ProgressBar>(R.id.progressBar)
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
//                progressDialog!!.setTitle("Loading...")
//                progressDialog!!.setMessage("Please wait...")
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.show()
                progressbar.visibility = View.VISIBLE
            }

            override fun onPageCommitVisible(view: WebView, url: String) {
                super.onPageCommitVisible(view, url)
//                if (progressDialog != null) {
//                    progressDialog!!.dismiss()
//                }
                progressbar.visibility = View.GONE
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}