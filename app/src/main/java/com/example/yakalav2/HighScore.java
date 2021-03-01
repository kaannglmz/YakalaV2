package com.example.yakalav2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class HighScore extends AppCompatActivity {
    SharedPreferences sh;

    TextView textView;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        textView=findViewById(R.id.textView13);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int currentHscor= sh.getInt("highscore",0);
        textView.setText(String.valueOf(currentHscor));
    }
    @Override
    protected void onResume() {
        if (MainActivity2.ses != null) {

            if (!MainActivity2.ses.isPlaying()) {
                MainActivity2.ses.start();
            }}
        super.onResume();
    }

    @Override
    protected void onPause() {
        MainActivity2.ses.pause();
        super.onPause();
    }
}