package com.bba.playdate1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by spielmohitp on 4/10/2017.
 */
public class TermsActivity extends AppCompatActivity {

    WebView webView;
    TextView BackImg;
    Intent intent;
    ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);


        webView = (WebView) findViewById(R.id.webView);
        BackImg = (TextView) findViewById(R.id.backtxt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        TextView titketct = (TextView) findViewById(R.id.tiltetxt);



        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://play-date.ae/terms.html");
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });


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
