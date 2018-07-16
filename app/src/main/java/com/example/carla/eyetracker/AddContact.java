package com.example.carla.eyetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddContact extends AppCompatActivity {

    private ImageButton AddButton;
    private EditText Editname;
    private EditText EditNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        final ContactDAO ContDAO =new ContactDAO(this);
        ContDAO.open();

        AddButton = (ImageButton)findViewById(R.id.button) ;

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editname = (EditText)findViewById(R.id.name);
                EditNumber =(EditText)findViewById(R.id.number);
                String name = Editname.getText().toString();
                String number= EditNumber.getText().toString();
                Contacts C=new Contacts(1,name,number);
                ContDAO.insertContact(C);
                finish();
            }
        });
    }
}
