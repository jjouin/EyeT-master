package com.example.carla.eyetracker;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Integer _activity = _idShopAct;
    private static Integer _idShopAct = 0;
    private static Integer _idDirAct = 1;
    private String  _strShopList;
    private String _strDirAct;

    private Button ButtonYes;
    private Button ButtonNo;
    private Button ButtonExit;
    private TextView ActText;
    private Integer index =0;
    private TextToSpeech tts;
    ArrayList<String> numberlist = new ArrayList<>();




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
            _strShopList = obj.getString("start_shoplist_activity");
            _strDirAct = obj.getString("start_directory_activity");

        } catch (IOException e) {
            Log.i("Coucou", "exc1");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.i("Coucou", "exc2");
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        get_json();




        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.UK);
                    if (_activity == _idShopAct) {
                        ActText.setText(_strShopList);
                        TTS(_strShopList);
                    }
                    else
                    {
                        ActText.setText(_strDirAct);
                        TTS(_strDirAct);
                    }

                }

            }
        });



            ButtonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_activity == _idShopAct)
                    {
                        Intent otherAct = new Intent(getApplicationContext(), ShopList.class);
                        startActivity(otherAct);
                        //_activity=_idDirAct;
                    }
                    if (_activity==_idDirAct)
                    {
                        Intent otherAct = new Intent(getApplicationContext(), Directory.class);
                        startActivity(otherAct);
                       // _activity=_idShopAct;
                    }
                }
            });

            ButtonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _activity = (_activity + 1) % 2;
                    if (_activity == _idShopAct)
                    {
                        ActText.setText(_strShopList);
                        TTS(_strShopList);
                    }

                    if (_activity == _idDirAct) {
                        ActText.setText(_strDirAct);
                        TTS(_strDirAct);

                    }
                }
            });





    }


    public void TTS(String s)
    {

        tts.speak(s, TextToSpeech.QUEUE_FLUSH,null);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        }
    }
