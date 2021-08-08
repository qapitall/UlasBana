package com.quince_tech.ulasbana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    MediaPlayer mp;
    TextView score;
    EditText name;
    Button b1;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int sizeSave;
    String puan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent yeni = getIntent();
        puan = yeni.getStringExtra("puan");

        pref=this.getSharedPreferences("UlasBanaByQuinceTech", Context.MODE_PRIVATE);
        editor = pref.edit();

        sizeSave = pref.getInt("sizeForSave",0);

        score = findViewById(R.id.score_text);
        name = findViewById(R.id.editText);
        b1 = findViewById(R.id.button3);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("***********************").build();
        adView.loadAd(adRequest);

        int test = Integer.parseInt(puan.replace("Skor: ",""));
        if(test == 0) {
            b1.setClickable(false);
            b1.setTextColor(Color.GRAY);
            name.setVisibility(View.INVISIBLE);
            score.setText(puan+"\n Tekrar Deneyin" );
        }else
            score.setText(puan+"\n Tebrikler" );
        mp = MediaPlayer.create(this,R.raw.sample);

        score.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    save();
                    return true;
                }
                return false;
            }
        });
    }

    public void oyunBaslasın (View view){ // Start Button (onClick)
        mp.start();
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }


    public void kaydet(View view){
        save();
    }
    public void save(){
        mp.start();
        if(name.getText().length()<1){
            Toast.makeText(this,"İsim girmelisiniz.",Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            Date tarih = new Date();
            SimpleDateFormat dakika = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Toast.makeText(this,"SKOR KAYDEDİLDİ",Toast.LENGTH_SHORT).show();
            sizeSave++;
            String puanForSave = puan.replace("Skor: ","");
            editor.putInt("sizeForSave",sizeSave);
            editor.putString("save"+sizeSave,"{\"Puan\":\""+ puanForSave +"\",\"Tarih\":\""+dakika.format(tarih) +"\",\"User\":\""+name.getText().toString()+"\"}");
            editor.commit();}
        catch (Exception e){
            Toast.makeText(this,"Beklenmedik bir hata oluştu.",Toast.LENGTH_SHORT).show();
        }
        score.setVisibility(View.VISIBLE);
        b1.setClickable(false);
        b1.setTextColor(Color.GRAY);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
