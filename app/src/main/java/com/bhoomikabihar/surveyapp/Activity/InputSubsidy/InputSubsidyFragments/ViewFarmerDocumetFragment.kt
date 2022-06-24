package com.dbtagri.dbtagriverify.Activity.InputSubsidy.InputSubsidyFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.bhoomikabihar.surveyapp.R


class ViewFarmerDocumetFragment : Fragment() {
    companion object {
        fun newInstance() = ViewFarmerDocumetFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_view_farmer_documet, container, false)

        val webView = root.findViewById(R.id.webView) as WebView
//        webView.settings.javaScriptEnabled = true
        webView.webViewClient = Callback()
////        val url = "path of remote pdf"
//        webView.loadUrl("http://164.100.130.206/FDSNew/Images/SelfDeclarationFlood.pdf")


        //webView!!.webViewClient = WebViewClient()

        //webView = findViewById(R.id.webview) as WebView
        // progressbar = findViewById(R.id.progressbar) as ProgressBar
        webView!!.getSettings().setJavaScriptEnabled(true)
        val name = ""
        //"http://164.100.130.206/FDSNew/Images/SelfDeclarationFlood.pdf"
        //intent.getStringExtra("filePath")
        val filename =
            "164.100.130.206/FDSNew/Images/SelfDeclarationFlood.pdf$name"
        var url = "http://docs.google.com/gview?embedded=true&url=$filename"
        webView!!.loadUrl(url)

//        webView!!.setWebViewClient(object : WebViewClient() {
//            override fun onPageFinished(view: WebView, url: String) {
//                // do your stuff here
//                //  progressbar.setVisibility(View.GONE)
//            }
//        })
        return root
    }


    private class Callback : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView, url: String
        ): Boolean {
            return false
        }
    }
}