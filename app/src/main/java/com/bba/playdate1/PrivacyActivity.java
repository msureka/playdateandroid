package com.bba.playdate1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by spielmohitp on 4/11/2017.
 */
public class PrivacyActivity extends AppCompatActivity {


    //variables
    WebView webView;
    TextView BackImg;
    Intent intent;
    ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        webView = (WebView) findViewById(R.id.webView);
        BackImg = (TextView) findViewById(R.id.backtxt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);


        //call url in webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://play-date.ae/privacy.html");
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                //set progressbar
                progressBar.setProgress(progress);
                if (progress == 100) {
                    //on complted
                    //hide progresbar
                    progressBar.setVisibility(View.GONE);

                } else {
                    //on progress
                    //visible progressbar
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });


        //Back btn on click
        BackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
