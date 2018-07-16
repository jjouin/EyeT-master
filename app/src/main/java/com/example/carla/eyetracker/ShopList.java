package com.example.carla.eyetracker;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ShopList extends AppCompatActivity {
    private Integer _activity=_idAdd;
    private static Integer _idRead = 1;
    private static Integer _idAdd = 0;
    private static Integer _idDel = 2;

    private Button ButtonYes;
    private Button ButtonNo;
    private Button ButtonExit;
    private TextView ActText;
    private TextToSpeech tts;
    private String _strAddItem;
    private String _strDelSL;
    private String _strReadSL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_json();
        setContentView(R.layout.activity_shop_list);


        ActText = findViewById(R.id.textAct);
        ButtonExit = findViewById(R.id.buttonExit);
        ButtonYes = findViewById(R.id.buttonYes);
        ButtonNo = findViewById(R.id.buttonNo);


        ButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




            ButtonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_activity==_idAdd) {
                        Intent otherAct = new Intent(getApplicationContext(),AddShopList.class);
                        startActivity(otherAct);
                    }
                    if (_activity == _idRead)
                    {
                        Intent otherAct = new Intent(getApplicationContext(),ReadShopList.class);
                        startActivity(otherAct);
                    }
                    if (_activity == _idDel)
                    {
                        ItemsDAO itemsDAO = new ItemsDAO(getApplicationContext());
                        itemsDAO.open();
                        itemsDAO.DeleteAllItems ();

                    }
                    _activity=(_activity+1)%3;
                    if (_activity==_idAdd) {
                        ActText.setText(_strAddItem);
                        TTS(_strAddItem);
                    }



                }
            });

            ButtonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _activity=(_activity+1)%3;
                    if (_activity==_idAdd) {
                        ActText.setText(_strAddItem);
                        TTS(_strAddItem);
                    }
                    if (_activity==_idDel) {
                        ActText.setText(_strDelSL);
                        TTS(_strDelSL);
                    }
                    if (_activity == _idRead) {
                        ActText.setText(_strReadSL);
                        TTS(_strReadSL);
                    }
                        }
            });


    }

    public void TTS(String s)
    {

        tts.speak(s, TextToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    protected void onResume() {

        super.onResume();
        get_json();

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.UK);
                    if (_activity==_idAdd) {
                        ActText.setText(_strAddItem);
                        TTS(_strAddItem);
                    }
                    if (_activity==_idDel){
                        ActText.setText(_strDelSL);
                        TTS(_strDelSL);
                    }

                    if (_activity == _idRead) {
                        ActText.setText(_strReadSL);
                        TTS(_strReadSL);
                    }

                }

            }
        });

    }
    @Override
    protected void onPause()
    {
        if (tts!=null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void get_json()
    {
        String json;
        try
        {
            InputStream is = getAssets().open("en.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json= new String(buffer,"UTF-8");
            JSONObject obj = new JSONObject(json);
            _strAddItem = obj.getString("add_item_shoplist");
            _strDelSL = obj.getString("del_shoplist");
            _strReadSL = obj.getString("read_shoplist");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
