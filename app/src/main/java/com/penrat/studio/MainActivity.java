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
        "    console.log('🚀 Android Injector: Starting...');" +
        "    const css = `" +
        "        [_ngcontent-ng-c1193458499]," +
        "        header, footer, .ms-app-header, .ms-app-footer, .mat-toolbar, ms-app-header, ms-app-sidebar, " +
        "        [role='banner'], .ms-sidebar, .left-panel {" +
        "            display: none !important; height: 0 !important; overflow: hidden !important;" +
        "        }" +
        "        body, html { margin: 0 !important; padding: 0 !important; overflow: hidden !important; background: #000 !important; }" +
        "        iframe, ms-app-preview {" +
        "            position: fixed !important; top: 0 !important; left: 0 !important;" +
        "            width: 100vw !important; height: 100vh !important;" +
        "            border: none !important; z-index: 2147483647 !important; background: #fff !important;" +
        "        }" +
        "    `;" +
        "    const style = document.createElement('style');" +
        "    style.textContent = css;" +
        "    document.documentElement.appendChild(style);" +
        "    const observer = new MutationObserver(() => {" +
        "        const frames = document.querySelectorAll('iframe, ms-app-preview');" +
        "        frames.forEach(f => {" +
        "            if (f.style.position !== 'fixed') {" +
        "                f.style.setProperty('position', 'fixed', 'important');" +
        "                f.style.setProperty('top', '0', 'important');" +
        "                f.style.setProperty('width', '100vw', 'important');" +
        "                f.style.setProperty('height', '100vh', 'important');" +
        "                f.style.setProperty('z-index', '2147483647', 'important');" +
        "            }" +
        "        });" +
        "    });" +
        "    observer.observe(document.documentElement, { childList: true, subtree: true, attributes: true });" +
        "})()";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Set Fullscreen
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        
        // Enable JavaScript and Storage
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");

        // Force Desktop Mode if needed (optional, currently using Mobile UA)
        // webSettings.setLoadWithOverviewMode(true);
        // webSettings.setUseWideViewPort(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // INJECT THE CLEANER SCRIPT
                view.evaluateJavascript(CLEANER_JS, null);
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
