package com.example.carla.eyetracker;

import android.content.Intent;
import android.net.Uri;
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

public class Directory extends AppCompatActivity {

    private List<HashMap<String, String>> _list;
    private Integer index =0;
    private TextToSpeech tts;
    private Integer nbArt;
    private Button ButtonYes;
    private Button ButtonNo;
    private Button ButtonExit;
    private TextView ActText;
    private Button ButtonAdd;
    private String _strNoContact;
    private String _strCall;
    private String _strTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        ButtonYes= findViewById(R.id.buttonYes);
        ButtonExit= findViewById(R.id.buttonExit);
        ButtonNo =findViewById(R.id.buttonNo);
        ActText = findViewById(R.id.textItem);
        ButtonAdd = findViewById(R.id.buttonAdd);
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

    @Override
    protected void onStart() {
        super.onStart();
        get_json();
        final ContactDAO contactDAO = new ContactDAO(this);
        contactDAO.open();
        _list = contactDAO.selectALL();
        nbArt=_list.size();

        ButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(), AddContact.class);
                startActivity(otherAct);
            }
        });



        if (_list.isEmpty()) {
            ActText.setText(_strNoContact);
            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status== TextToSpeech.SUCCESS)
                    {
                        tts.setLanguage(Locale.UK);
                        TTS(_strNoContact);
                    }

                }
            });
            ButtonYes.setVisibility(View.INVISIBLE);
            ButtonNo.setVisibility(View.INVISIBLE);
        }


        else {
            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        tts.setLanguage(Locale.UK);
                        TTS(_strCall + _list.get(index).get("Text1") + " ?");
                    }

                }
            });

            ActText.setText(_strCall + _list.get(index).get("Text1") + " ?");

            ButtonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index++;
                    if (index == nbArt)
                        index= 0;
                    ActText.setText(_strCall + _list.get(index).get("Text1") + " ?");
                    TTS(_strCall + _list.get(index).get("Text1") + " ?");
                }
            });

            ButtonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String number = _list.get(index).get("Text2");
                    Uri telephone = Uri.parse(_strTel +number);
                    Intent secondActivity = new Intent(Intent.ACTION_DIAL, telephone);
                    startActivity(secondActivity);
                }
            });
        }
    }

    public void TTS(String s)
    {
        tts.speak(s, TextToSpeech.QUEUE_FLUSH,null);

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
            JSONObject obj =  new JSONObject(json);
            _strNoContact  = obj.getString("no_contact_available");
            _strCall= obj.getString("call_contact");
            _strTel = obj.getString("tel");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
