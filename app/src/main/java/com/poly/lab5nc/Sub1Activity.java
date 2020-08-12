package com.poly.lab5nc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Sub1Activity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);
        webView = findViewById(R.id.my_web);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        Log.e("a", link);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);

    }
}
