package com.digitopia.temiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnBatteryStatusChangedListener;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionDataChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnMovementVelocityChangedListener;
import com.robotemi.sdk.listeners.OnRobotLiftedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;
import com.robotemi.sdk.map.OnLoadMapStatusChangedListener;
import com.robotemi.sdk.permission.OnRequestPermissionResultListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebviewActivity extends AppCompatActivity {

    public static WebView webView;
    public static Robot sRobot = MainActivity.sRobot;
    private static String WEBVIEW_TAG = "WEBVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // https://developer.android.com/guide/webapps/webview
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        //webView.setWebViewClient(new WebViewClient());

        // get the intent that started this activity and extract the webview URL
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.WEBVIEW_URL);


        // TODO DONT FORGET TO REMOVE CLEAR CACHE

        webView.addJavascriptInterface(new JavascriptHandler(), "Android");



        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            // Grant permissions for cam
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(WEBVIEW_TAG, "onPermissionRequest");
                MainActivity.fa.runOnUiThread(() -> {
                    request.grant(request.getResources());
                    Log.d(WEBVIEW_TAG, request.getOrigin().toString());
                });
            };
        });

        webView.clearCache(true);
        webView.loadUrl(url);

        loadListeners();
        //NDIActivity.main();
    }

    private void loadListeners() {
        sRobot.addOnRobotReadyListener((OnRobotReadyListener) MainActivity.fa);
        sRobot.addOnBatteryStatusChangedListener((OnBatteryStatusChangedListener) MainActivity.fa);
        sRobot.addOnGoToLocationStatusChangedListener((OnGoToLocationStatusChangedListener) MainActivity.fa);
        sRobot.addOnDetectionStateChangedListener((OnDetectionStateChangedListener) MainActivity.fa);
        sRobot.addOnUserInteractionChangedListener((OnUserInteractionChangedListener) MainActivity.fa);
        sRobot.addOnRequestPermissionResultListener((OnRequestPermissionResultListener) MainActivity.fa);
        sRobot.addOnRobotLiftedListener((OnRobotLiftedListener) MainActivity.fa);
        sRobot.addOnMovementVelocityChangedListener((OnMovementVelocityChangedListener) MainActivity.fa);
        sRobot.addOnBeWithMeStatusChangedListener((OnBeWithMeStatusChangedListener) MainActivity.fa);
        sRobot.addOnLoadMapStatusChangedListener((OnLoadMapStatusChangedListener) MainActivity.fa);
        sRobot.addOnDetectionDataChangedListener((OnDetectionDataChangedListener) MainActivity.fa);
    }

    public static void destroyWebView() {

        // Make sure you remove the WebView from its parent view before doing anything.
        //mWebContainer.removeAllViews();

        webView.clearHistory();

        // NOTE: clears RAM cache, if you pass true, it will also clear the disk cache.
        // Probably not a great idea to pass true if you have other WebViews still alive.
        //webView.clearCache(true);

        // Loading a blank page is optional, but will ensure that the WebView isn't doing anything when you destroy it.
        webView.loadUrl("about:blank");

        webView.onPause();
        webView.removeAllViews();
        webView.destroyDrawingCache();

        // NOTE: This can occasionally cause a segfault below API 17 (4.2)
        webView.destroy();

        // Null out the reference so that you don't end up re-using it.
        webView = null;
    }
}