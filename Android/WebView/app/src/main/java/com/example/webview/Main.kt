package com.example.webview

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class Main : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        webView = findViewById(R.id.webview)
        webView.webViewClient = MyWebViewClient()

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl("https://stock.tw-dvd.us/webview")
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }

    /*override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ||
            newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val currentUrl = webView.url
            val parentLayout = webView.parent as? ViewGroup
            parentLayout?.removeView(webView)

            webView = WebView(this)
            parentLayout?.addView(webView)

            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true

            if (!currentUrl.isNullOrBlank()) {
                webView.loadUrl(currentUrl)
            }
        }
    }*/

}

private class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url!!)
        return true
    }
}