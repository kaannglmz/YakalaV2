package com.example.yakalav2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // App Id: ca-app-pub-2513329140963237~8690721733
    // Interstitial TEST Id : ca-app-pub-3940256099942544/1033173712
    // Interstitial Ad Id: ca-app-pub-2513329140963237/9736317897

    private InterstitialAd mInterstitialAd;
ImageView imageView;
ImageView imageView2;
ImageView imageView9;
ImageView imageView10;
TextView scoreText;
TextView timeText;
SharedPreferences sharedPreferences;

int x,y,q,w,a,b,p,r;
int score=0;
CountDownTimer countDownTimer,countDownTimer2;
long second=150000;
long interval=1000;

public static MediaPlayer bite,clank;//Eklenecek sesleri burada tanımlayıp projenin içine bir ses dosyası açıyoruz ve sesleri oraya atıyoruz.Sonrasında onCreate içinde bağlantısını kuruyoruz ve gerekli yerde çağırıyoruz.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        Display display=getWindowManager().getDefaultDisplay(); //Ekran genişliğini ve yüksekliğini buluyoruz.

        Point point=new Point();

        display.getSize(point);
        x=point.x;
        y=point.y;
        q=point.x;
        w=point.y;
        a=point.x;
        b=point.y;
        p=point.x;
        r=point.y;

        scoreText=findViewById(R.id.textScore);
        timeText=findViewById(R.id.textTime);
        imageView=findViewById(R.id.imageView);

        imageView2=findViewById(R.id.imageView2);
        imageView9=findViewById(R.id.imageView9);
        imageView10=findViewById(R.id.imageView10);

        bite=MediaPlayer.create(MainActivity.this,R.raw.bite);
        clank=MediaPlayer.create(MainActivity.this,R.raw.clank);
        basla();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
    public void basla() {


        countDownTimer = new CountDownTimer(second, interval) {
            public void onTick(long milisaniye) {
                second -= 1000;
                int rndm = new Random().nextInt(14);
                timeText.setText("Time: " + milisaniye / 1000);
                if (rndm < 9) {
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView9.setVisibility(View.INVISIBLE);
                    imageView10.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    float rndmX = new Random().nextInt(x - imageView.getMeasuredWidth() * 2);
                    float rndmY = new Random().nextInt(y - imageView.getMeasuredHeight() * 2)+150;
                    imageView.setX(rndmX);
                    imageView.setY(rndmY);
                } else if (rndm >= 9 && rndm < 11) {
                    imageView9.setVisibility(View.INVISIBLE);
                    imageView10.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    float rndmx = new Random().nextInt(q - imageView2.getMeasuredWidth() * 2);
                    float rndmy = new Random().nextInt(w - imageView2.getMeasuredHeight() * 2+150);
                    imageView2.setX(rndmx);
                    imageView2.setY(rndmy);
                } else if (rndm >= 11 && rndm < 12) {
                    imageView.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView10.setVisibility(View.INVISIBLE);
                    imageView9.setVisibility(View.VISIBLE);
                    float rndmX = new Random().nextInt(a - imageView9.getMeasuredWidth() * 2);
                    float rndmY = new Random().nextInt(b - imageView9.getMeasuredHeight() * 2)+150;
                    imageView9.setX(rndmX);
                    imageView9.setY(rndmY);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView9.setVisibility(View.INVISIBLE);
                    imageView10.setVisibility(View.VISIBLE);
                    float rndmX = new Random().nextInt(p - imageView10.getMeasuredWidth() * 2);
                    float rndmY = new Random().nextInt(r - imageView10.getMeasuredHeight() * 2)+150;
                    imageView10.setX(rndmX);
                    imageView10.setY(rndmY);
                }
            }
            public void onFinish() {
                timeText.setText("Time: -");
                imageView.setVisibility(View.INVISIBLE);
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putString("score", String.valueOf(score)).apply();
                int highScore = sharedPreferences.getInt("highscore", 0);
                Log.e("AppX", "sc: " + String.valueOf(score) + " Hsc:" + String.valueOf(highScore));
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                if (score > highScore) {
                    sharedPreferences.edit().putInt("highscore", score).apply();
                }
                if (!isFinishing()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Restart");
                    alert.setMessage("Do you want try again?");
                    alert.setCancelable(false);

                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                            imageView.setVisibility(View.VISIBLE);
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Game Over.", Toast.LENGTH_LONG);
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            finish();
                            startActivity(intent);
                        }
                    });
                    alert.show();
                }
            }}.

            start();
        }
    public void arttir(View view) {
        score++;
        if(score%3==0){
            second=second+1000;
        }
        scoreText.setText("Score: "+score);
        if(score%3==0){
            interval-=50;
            }
        bite.start();
     newcountdown();
    }
    public void azalt(View view) {
        second=second-2000;
        scoreText.setText("Score: "+score);
        clank.start();
       newcountdown();
    }
    public void arttir2(View view) {
        score+=2;
        second+=2000;
        scoreText.setText("Score: "+score);
        bite.start();
      newcountdown();
    }
    public void azalt2(View view) {
        second=second-5000;
        scoreText.setText("Score: "+score);
        clank.start();
       newcountdown();
    }

    public void newcountdown(){
        if (countDownTimer != null)
            countDownTimer.cancel();
        basla();
    }
}







