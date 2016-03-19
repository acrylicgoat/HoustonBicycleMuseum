/**
 * Copyright (c) 2016 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.houstonbicyclemuseum.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.acrylicgoat.houstonbicyclemuseum.R;

public class WebviewActivity extends Activity
{
    private boolean progressBarRunning;
    private WebView webView;
    private String content;

    private WebViewClient webViewClient = new WebViewClient()
    {
        @Override
        public void onLoadResource(WebView view, String url)
        {
            super.onLoadResource(view, url);

            //Log.d("WebViewClient.onLoadResource()", "Called");
        }

        /** loads URL into view */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            //Log.d("WebViewClient.shouldOverrideUrlLo()", "Called");
            if(!progressBarRunning)
            {
                setProgressBarIndeterminateVisibility(true);
            }
            view.loadUrl(url);

            return true;
        }

        /* (non-Javadoc)
         * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
         * Sets title and URL correctly after the page is fully loaded
         */
        @Override
        public void onPageFinished(WebView view, String url)
        {
            //Log.d("WebViewClient.onPageFinished", "title: " + view.getTitle());
            //Log.d("WebViewClient.onPageFinished", "url: " + url);

            setProgressBarIndeterminateVisibility(false);
            progressBarRunning = false;
            //Log.d("WebViewClient.onPageFinished", "setSupportProgressBarIndeterminateVisibility(false) Called");

        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        //Log.d("LensWebView.onCreate()", "Called");

        setContentView(R.layout.webview);
        setProgressBarIndeterminateVisibility(true);
        Intent intent = getIntent();
        content = (String)intent.getSerializableExtra("webcontent");
        //Log.d("onCreate()",content);

        webView = (WebView)findViewById(R.id.web_view);
        setUpViews();

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        //handle changing menu based on URL
        return onCreateOptionsMenu(menu);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     * Handles use of back button on browser
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(webView != null && ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()))
        {

            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     * added to handle orientation change.  Not sure why this is needed, but it is.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
       super.onResume();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");

    }

    /** sets properties on WebView and loads selected content into browser. */
    private void setUpViews()
    {

        //Log.d("WebViewView.setupViews()", "Called");
        webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setDefaultFontSize(17);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebChromeClient(new WebChromeClient()
        {


        });

        webView.setWebViewClient(webViewClient);
        webView.loadUrl(content);
    }

}
