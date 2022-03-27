package com.jimrp.cosmoscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivityWebView extends AppCompatActivity {

    private Button btnPrint, btnRescan, btnThermal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web_view);

        btnPrint = (Button) findViewById(R.id.button1);
        btnRescan = (Button) findViewById(R.id.button2);
        btnThermal = (Button) findViewById(R.id.button3);
        WebView myWebView = (WebView) findViewById(R.id.webviewdefault);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl(MainActivity.url);

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWebPagePrint(myWebView);
            }
        });

        btnRescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnThermal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public  void createWebPagePrint(WebView webView) {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        String jobName = getString(R.string.app_name) + " Document";
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        //builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if(printJob.isCompleted()){
            Toast.makeText(getApplicationContext(), "complete", Toast.LENGTH_LONG).show();
        }
        else if(printJob.isFailed()){
            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}