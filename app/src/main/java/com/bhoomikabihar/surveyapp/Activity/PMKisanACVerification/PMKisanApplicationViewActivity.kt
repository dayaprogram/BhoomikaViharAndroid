package com.bhoomikabihar.surveyapp.Activity.PMKisanACVerification

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bhoomikabihar.surveyapp.R


class PMKisanApplicationViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val webView = findViewById<WebView>(R.id.webViewActivity)
        webView!!.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        val progressbar = findViewById<ProgressBar>(R.id.progressBar)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh);
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        val url = intent.getStringExtra("URL")!!
//        val filename ="https://dbtagriculture.bihar.gov.in/regfarmer/pmkisanadmin/ViewDocument.aspx?Rid=$name"
//        var url="https://drive.google.com/viewerng/viewer?embedded=true&url=$filename"

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

        swipeRefreshLayout.setOnRefreshListener {
            progressbar.visibility = View.VISIBLE
            webView.loadUrl(url)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    progressbar.visibility = View.GONE
                }
            }       // refresh your list contents somehow
            swipeRefreshLayout.isRefreshing =
                false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}