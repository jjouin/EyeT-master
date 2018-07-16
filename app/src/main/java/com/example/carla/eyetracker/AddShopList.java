package com.example.carla.eyetracker;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddShopList extends Activity {
    private Integer nbArt;
    private Button ButtonYes;
    private Button ButtonNo;
    private Button ButtonExit;
    private TextView ActText;
    private String _strNoItem;
    private String _strAdd;
    private List<HashMap<String, String>> _list;
    private Integer index =0;
    private TextToSpeech tts;
    int result;

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
        final ItemsDAO itemsDAO = new ItemsDAO(this);
        itemsDAO.open();
        _list = itemsDAO.noSelectedList();
        nbArt= _list.size();

        ButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if (_list.isEmpty()) {
            ActText.setText(_strNoItem);
            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status== TextToSpeech.SUCCESS)
                    {
                        tts.setLanguage(Locale.UK);
                        TTS(_strNoItem);
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
                        result = tts.setLanguage(Locale.UK);
                        TTS(_strAdd + _list.get(index).get("Text1") + " ?");
                    }

                }
            });

            ActText.setText( _strAdd + _list.get(index).get("Text1") + " ?");

            ButtonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index++;
                    if (index < nbArt) {
                        ActText.setText(_strAdd + _list.get(index).get("Text1") + " ?");
                        TTS(_strAdd + _list.get(index).get("Text1") + " ?");
                    } else {
                        Intent otherAct = new Intent(getApplicationContext(),AddShopList.class);
                        startActivity(otherAct);
                        finish();
                        /*index = 0;
                        ActText.setText("Add : " + _list.get(index).get("Text1") + " ?");
                        TTS("Add : " + _list.get(index).get("Text1") + " ?");*/
                    }

                }
            });

            ButtonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items i = itemsDAO.selectByID(getItemId(index));
                    itemsDAO.upDateItems(i);
                    index++;
                    if (index < nbArt) {
                        ActText.setText(_strAdd + _list.get(index).get("Text1") + " ?");
                        TTS(_strAdd + _list.get(index).get("Text1") + " ?");
                    } else {
                        finish();
                        Intent otherAct = new Intent(getApplicationContext(),AddShopList.class);
                        startActivity(otherAct);
                        finish();
                       /* _list = itemsDAO.noSelectedList();
                        nbArt = _list.size();
                        if (nbArt > 0) {
                            index = 0;
                            ActText.setText("Add : " + _list.get(index).get("Text1") + " ?");
                            TTS("Add : " + _list.get(index).get("Text1") + " ?");
                        } else {
                            ActText.setText("Shoplist is empty");
                            TTS("Shoplist is empty");
                            ButtonYes.setVisibility(View.INVISIBLE);
                            ButtonNo.setVisibility(View.INVISIBLE);
                        }*/

                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_list);

        ButtonYes= findViewById(R.id.buttonYes);
        ButtonExit= findViewById(R.id.buttonExit);
        ButtonNo =findViewById(R.id.buttonNo);
        ActText = findViewById(R.id.text);

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
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
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
            _strNoItem = obj.getString("no_item");
            _strAdd = obj.getString("add_item");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
