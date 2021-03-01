package com.example.yakalav2;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity2 extends AppCompatActivity {

TextView textView;
SharedPreferences sh;
String score="Not Found";
public static MediaPlayer ses;
FirebaseDatabase database;
DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("ID");
        databaseReference.setValue("Kaan");


        textView = findViewById(R.id.highscore);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        score = sh.getString("score", "Not Found");
        textView.setText(String.valueOf("SCORE: "+score));
sh.getBoolean("check",true);

        if (score.equals("Not Found")) {
            textView.setText("SCORE:0");
        }

    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {
        if (ses == null) {
            ses=MediaPlayer.create(MainActivity2.this,R.raw.ses);
            ses.setLooping(true);
        }
        if (!ses.isPlaying()) {
            ses.start();
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        ses.pause();
        super.onPause();
    }


    public void baslat(View view) {
        Intent intent=new Intent(MainActivity2.this,MainActivity.class);

        startActivity(intent);
    }


    public void highScore(View view) {
        Intent intent=new Intent(MainActivity2.this,HighScore.class);
        startActivity(intent);
    }

    public void help(View view) {

        Intent intent=new Intent(MainActivity2.this,Help.class);
        startActivity(intent);
        
    }

    public void info(View view) {
        Intent intent=new Intent(MainActivity2.this,Info.class);
        startActivity(intent);
    }

    public void quit(View view) {
        finishAffinity();
    }


}