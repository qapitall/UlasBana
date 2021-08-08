package com.quince_tech.ulasbana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.quince_tech.ulasbana.Adapter.MyAdapter;
import com.quince_tech.ulasbana.Model.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    private List<Item> items=new ArrayList<>();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int sizeSave;
    private RecyclerView recyclerVi;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        pref=this.getSharedPreferences("UlasBanaByQuinceTech", Context.MODE_PRIVATE);
        editor = pref.edit();

        sizeSave = pref.getInt("sizeForSave",0);
        recyclerVi=findViewById(R.id.recyc);
        recyclerVi.setLayoutManager(new LinearLayoutManager(this));

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("*****************").build();
        adView.loadAd(adRequest);

        myAdapter=new MyAdapter(recyclerVi,this,items,pref);
        getSaved();
    }

    public void getSaved(){

        items.clear();
        String msg = null,time = null,type = null, rc = null;

        for(int i=1 ; i<=sizeSave;i++){
            String data = pref.getString("save"+i,"Error");
            if(data.compareTo("Error")!=0) {
                JSONObject json=null;
                try {

                    json=new JSONObject(data);
                    msg =json.getString("Puan");
                    time=json.getString("Tarih");
                    type=json.getString("User");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    sort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Item item = new Item(time,type,msg,i);
                items.add(item);
            }
        }

        sort();
        Collections.reverse(items);
        recyclerVi.setAdapter(myAdapter);
    }

    public void sort(){

        int i,j;

        for (i = 1; i < items.size(); i++) {
            Item key = new Item("", "","",0);
            key.setTime(items.get(i).getTime());
            key.setMessage(items.get(i).getMessage());
            key.setId(items.get(i).getId());
            key.setPoint(items.get(i).getPoint());
            j = i;
            while((j > 0) && (Integer.parseInt(items.get(j - 1).getPoint()) > Integer.parseInt(key.getPoint()))) {
                items.set(j,items.get(j - 1));
                j--;
            }
            items.set(j,key);
        }
    }
}
