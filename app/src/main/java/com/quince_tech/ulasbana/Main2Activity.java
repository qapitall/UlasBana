package com.quince_tech.ulasbana;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    Button n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16;
    public static Button [] textArray;
    static public boolean bold=false;
    public int number =0;
    TextView generatedNum,leftTime,score_text, level_text ;
    public TextView SumText;
    private static final int TIME=30;
    int  range ,index ,derece=5,sure=TIME ;
    public int hamle=0;
    public static int puan;
    public static CountDownTimer ct;
    public static ArrayList<Integer> indexs = new ArrayList<>();
    public static ArrayList<Integer> list1 = new ArrayList<>();
    public static ArrayList<Integer> list2 = new ArrayList<>();
    public static ArrayList<Integer> main_list1 = new ArrayList<>();
    public static ArrayList<Integer> main_list2 = new ArrayList<>();
    public static ArrayList<Integer> total = new ArrayList<>();
    boolean[] selected = new boolean [16];
    MediaPlayer mp;
    ToneGenerator t;
    Random random;
    private int level = 0,flag=0;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("****************");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("****************").build());


        generatedNum = findViewById(R.id.textNumber);
        leftTime=findViewById(R.id.textTime);
        score_text = findViewById(R.id.score_text);
        level_text = findViewById(R.id.levelText);
        mp = MediaPlayer.create(this,R.raw.sample);
        n1 = findViewById(R.id.text);
        n2 = findViewById(R.id.text2);
        n3 = findViewById(R.id.text3);
        n4 = findViewById(R.id.text4);
        n5 = findViewById(R.id.text5);
        n6 = findViewById(R.id.text6);
        n7 = findViewById(R.id.text7);
        n8 = findViewById(R.id.text8);
        n9 = findViewById(R.id.text9);
        n10 = findViewById(R.id.text10);
        n11 = findViewById(R.id.text11);
        n12 = findViewById(R.id.text12);
        n13 = findViewById(R.id.text13);
        n14 = findViewById(R.id.text14);
        n15 = findViewById(R.id.text15);
        n16 = findViewById(R.id.text16);
        SumText=findViewById(R.id.textSum);
        textArray = new Button[]{n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16};
        int i=0;
        for (Button myText : textArray){
            myText.setOnClickListener(this);
            myText.setTag(i);
            i++;
        }
        puan = 0;
        random = new Random();
        t= new ToneGenerator(AudioManager.STREAM_NOTIFICATION,100);
        startGame();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startGame();
            }

        });    }

    void startGame(){

        SumText.setText("Toplam: "+"0");
        level_text.setText("Seviye: "+(level+1));

        for(int i=0;i<selected.length;i++)
            selected[i]=false;
        for (Button myText : textArray) {
            myText.setText("");
            myText.setTextColor(Color.BLACK);
        }

        if(level<5){
            sure=TIME;
            range = 30; // bulunacak sayı aralığı
        }else if(level<10){
            if(flag==0){
            Toast.makeText(this,"Süreniz artık 25 saniye",Toast.LENGTH_SHORT).show();
            flag++;}
            range = 50; // bulunacak sayı aralığı
            sure=TIME-5;
        }else if(level<15){
            if(flag==1){
                Toast.makeText(this,"Süreniz artık 20 saniye",Toast.LENGTH_SHORT).show();
                flag++;}
            range = 75; // bulunacak sayı aralığı
            sure=TIME-10;
        }else if(level<20){
            if(flag==2){
                Toast.makeText(this,"Süreniz artık 15 saniye",Toast.LENGTH_SHORT).show();
                flag++;}
            range = 100; // bulunacak sayı aralığı
            sure=TIME-15;
        }else {
            if(flag==3){
                Toast.makeText(this,"Süreniz artık 10 saniye",Toast.LENGTH_SHORT).show();
                flag++;}
            range = 150; // bulunacak sayı aralığı
            sure=TIME-20;
        }
        total.clear();
        if(ct!=null)
            ct.cancel();
        ct =  new CountDownTimer(sure*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                sure--;
                leftTime.setText("Süre: "+String.valueOf(sure));

                //son 10 sn kalınca ses çıkartıyordu ama telefonda çalışmadı :( emülatörde çalışıyordu
                if(sure<10){
                   playSound(sure);

             }

            }
            void playSound(int rt){
               if (sure != 0)
                    t.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,500/sure);
            }

            @Override
            public void onFinish() { // süre bittiğinde

                sure=0;
                leftTime.setText("Süre Bitti !");

                if(mInterstitialAd.isLoaded())
                    mInterstitialAd.show();

                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                intent.putExtra("puan",score_text.getText().toString());
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

                for (Button myText : textArray) {
                    myText.setEnabled(false);
                    myText.setTextColor(Color.LTGRAY);
                }
                this.cancel();

                finish();
            }
        };
        ct.start();
        leftTime.setText("Süre: "+String.valueOf(sure));

        number = random.nextInt(range) + level*20;
        while (number < 10)
            number = random.nextInt(range) + level*20;

        generatedNum.setText(String.valueOf(number));
        main_list1.clear();
        main_list2.clear();
        total.clear();
        GenerateNumbers(number);


        index = random.nextInt(list1.size() - 1);

        main_list1.add(list1.get(index));
        main_list2.add(list2.get(index));

        while (derece > 0) {
            GenerateNumbers(list2.get(index));
            if(list1.size()-1==0)
                index=random.nextInt(1);
            else
                index = random.nextInt(list1.size() - 1);
            main_list1.add(list1.get(index));
            main_list2.add(list2.get(index));
            if (list2.get(index) < 2) {
                main_list1.addAll(main_list2);
                break;
            }
            derece--;
        }
        main_list1.add(list2.get(index));
        System.out.println("MAIN LIST: " + main_list1);

        GenerateIndexs(main_list1.size());

        for(int a=0, b=0 ;  b < main_list1.size(); a++ , b++) { //a < textArray.length-1 &
            textArray[indexs.get(a)].setText(String.valueOf(main_list1.get(b)));
        }

        for (Button myText : textArray) {
            while (myText.getText() == "")
                myText.setText(String.valueOf(random.nextInt(number)+1));
        }
    }

    void GenerateIndexs (int sayi){
        indexs.clear();

        int f;
        for(int i=0;i<sayi;i++) {
            f = random.nextInt(16);
            if(!indexs.contains(f))
                indexs.add(f);
            else{
                int y=random.nextInt(16);
                indexs.add(y);
            }
        }

    }

    void GenerateNumbers (int number){
        list1.clear();
        list2.clear();

        int len= number / 2;
        for (int i=1;i<len+1;i++){
            list1.add(i);
        }
        int a=0;
        int i=number-1;
        if(list1.size()>0) {
            while (a < list1.size()) {
                list2.add(i);
                i--;
                a++;
            }
        }

    }

    int Sum(ArrayList<Integer> m){
        int i;
        int sum = 0;
        for(i = 0; i < m.size(); i++)
            sum += m.get(i);
        return sum;
    }

    @Override
    public void onClick(View v) {
        Button btn=(Button)v;
        mp.start();
        SumText=findViewById(R.id.textSum);
        selected[(int)(v.getTag())]=!selected[(int)(v.getTag())];
        if(selected[(int)(v.getTag())]){
            ((Button) v).setTextColor(Color.WHITE);
            total.add(Integer.parseInt((String) btn.getText()));
            SumText.setText("Toplam: "+String.valueOf(Sum(total)));
            hamle++;
        }
        else{
            ((Button) v).setTextColor(Color.BLACK);
            int a = total.indexOf(Integer.parseInt((String) btn.getText()));
            if(!total.isEmpty()){
                total.remove(a);
                hamle--;
            }
            if(total.isEmpty())
                SumText.setText("Toplam: "+"0");
            else
                SumText.setText("Toplam: "+String.valueOf(Sum(total)));
        }

        if(number== Sum(total)) { // OYUN BITER, SAYIYI BULMASI DURUMU
            Toast.makeText(getApplicationContext(), "TRUE!", Toast.LENGTH_SHORT).show();
            puan+= 20+ (hamle*3);  // PUAN
//            Toast.makeText(getApplicationContext(), "Score:  "+ puan,Toast.LENGTH_SHORT).show();
            score_text.setText("Skor: "+String.valueOf(puan)); // puanı kullanıcı ekranına yaz
            hamle=0; //hamle sayısını sıfırla
            level++;

            if(mInterstitialAd.isLoaded()){
                mInterstitialAd.show();
                mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("**************************").build());
            }else
                startGame();
        }

    }

    @Override
    public void onBackPressed() {
        ct.cancel();
        this.finish();
    }
}
