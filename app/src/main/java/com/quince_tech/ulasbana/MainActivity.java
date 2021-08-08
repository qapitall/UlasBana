package com.quince_tech.ulasbana;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this,R.raw.sample);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("***********").build();
        adView.loadAd(adRequest);

    }


    public void oyunBaslasın (View view){ // Start Button (onClick)
        mp.start();
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
    public void siralama(View view){
        mp.start();
        Intent intent = new Intent(this,Main4Activity.class);
        startActivity(intent);
    }



}
