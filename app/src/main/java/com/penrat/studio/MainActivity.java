package com.penrat.studio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    // URL to load - Google AI Studio
    private static final String TARGET_URL = "https://aistudio.google.com/apps";

    // The Magic JS Payload to Clean UI
    private static final String CLEANER_JS = 
        "javascript:(function() {" +
        "    if (!window.location.hostname.includes('aistudio.google.com') || window.location.pathname.includes('login')) return;" +
        "    const style = document.createElement('style'); " +
        "    style.textContent = ` " +
        "        header, footer, .ms-app-header, .mat-toolbar, ms-app-header, .ms-sidebar, .left-panel { " +
        "            display: none !important; height: 0 !important; overflow: hidden !important; " +
        "        } " +
        "        body, html { margin: 0 !important; padding: 0 !important; background: #fff !important; } " +
        "        ms-app, .app-shell { display: block !important; visibility: visible !important; } " +
        "        iframe, ms-app-preview { " +
        "            position: fixed !important; top: 0 !important; left: 0 !important; " +
        "            width: 100vw !important; height: 100vh !important; " +
        "            z-index: 2147483647 !important; border: none !important; " +
        "        } " +
        "    `; " +
        "    document.documentElement.appendChild(style); " +
        "})()";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        
        // SWITCH TO DESKTOP UA - Much more stable for AI Studio
        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("aistudio.google.com") && !url.contains("accounts.google.com")) {
                    view.evaluateJavascript(CLEANER_JS, null);
                }
            }
        });

        if (savedInstanceState == null) {
            webView.loadUrl(TARGET_URL);
        }
    }
    
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
