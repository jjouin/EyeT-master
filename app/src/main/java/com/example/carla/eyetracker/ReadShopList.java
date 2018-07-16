package com.example.carla.eyetracker;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ReadShopList extends AppCompatActivity {

    private Integer nbArt;
    private Button ButtonDelete;
    private Button ButtonNext;
    private Button ButtonExit;
    private String _strSLempty;
    private String _strEndSL;
    private String _strRestart;
    private TextView ActText;
    private List<HashMap<String, String>> _list;
    private Integer index =0;
    TextToSpeech tts;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_json();
        setContentView(R.layout.activity_read_shop_list);

        ButtonDelete= findViewById(R.id.buttonDelete);
        ButtonExit= findViewById(R.id.buttonExit);
        ButtonNext =findViewById(R.id.buttonNext);
        ActText = findViewById(R.id.textItem);





        final ItemsDAO itemsDAO = new ItemsDAO(this);
        itemsDAO.open();
       _list = itemsDAO.selectedList();
        nbArt= _list.size();

        ButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (_list.isEmpty())
            ActText.setText(_strSLempty);

        else {

            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        result = tts.setLanguage(Locale.UK);
                        TTS(_list.get(index).get("Text1"));
                    }
                }
            });
            ActText.setText(_list.get(index).get("Text1"));


            ButtonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index++;

                    if (index < nbArt) {
                        ActText.setText(_list.get(index).get("Text1"));
                        TTS(_list.get(index).get("Text1"));
                    } else if (index==nbArt){
                        ActText.setText(_strEndSL);
                        TTS(_strEndSL);
                        ButtonNext.setText("Restart");
                        ButtonDelete.setVisibility(View.INVISIBLE);

                    }
                    else {
                        finish();
                        Intent otherAct = new Intent(getApplicationContext(),ReadShopList.class);
                        startActivity(otherAct);
                    }

                }
            });
        }

        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < nbArt) {
                    Items i = itemsDAO.selectByID(getItemId(index));
                    itemsDAO.upDateItems(i);
                }
                index++;
                if (index < nbArt) {
                    ActText.setText(_list.get(index).get("Text1"));
                    TTS(_list.get(index).get("Text1"));
                } else if (index==nbArt){
                    ActText.setText(_strEndSL);
                    TTS(_strEndSL);
                }
                else
                    finish();
            }
        });

    }

    public Long getItemId(int position)
    {
        HashMap<String, String> element =_list.get(position);
        String _id = element.get("id");
        return (Long.parseLong(_id));
    }



    public void TTS(String s)
    {
        if (result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
        {
            Toast.makeText(getBaseContext(), "Language not supported", Toast.LENGTH_LONG).show();
        }
        else
        {
            tts.speak(s, TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts!=null)
        {
            tts.stop();
            tts.shutdown();
        }

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
            _strSLempty = obj.getString("shoplist_empty");
            _strEndSL = obj.getString("end_shoplist");
            _strRestart = obj.getString("restart");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
