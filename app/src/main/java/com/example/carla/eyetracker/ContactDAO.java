package com.example.carla.eyetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactDAO extends DAOBase {



    public ContactDAO(Context pContext) {
        super(pContext);
    }


    public void insertContact(Contacts c)
    {
        ContentValues values = new ContentValues();
        values.put(DataBaseManager.Constants.KEY_COL_name, c.getName());
        values.put(DataBaseManager.Constants.KEY_COL_NUMBER, c.getNumberPhone());
        mDb.insert(DataBaseManager.Constants.myTable,null,values);
    }

    public void deleteContact( Long id )
    {
        mDb.delete(DataBaseManager.Constants.myTable, DataBaseManager.Constants.KEY_COL_ID +"=?", new String[]{String.valueOf(id)});
    }

    public void upDateContact (Contacts c)
    {
        ContentValues values = new ContentValues();
        values.put(DataBaseManager.Constants.KEY_COL_name, c.getName());
        values.put(DataBaseManager.Constants.KEY_COL_NUMBER, c.getNumberPhone());
        mDb.update(DataBaseManager.Constants.myTable, values, DataBaseManager.Constants.KEY_COL_ID+"=?", new String[]{String.valueOf(c.getId())});
    }

    public List<HashMap<String, String>> selectALL ()
    {
        List<HashMap<String, String>> _list = new ArrayList<HashMap<String, String>>() ;
        String[] column = {DataBaseManager.Constants.KEY_COL_name, DataBaseManager.Constants.KEY_COL_NUMBER, DataBaseManager.Constants.KEY_COL_ID};
        Cursor c = mDb.query(DataBaseManager.Constants.myTable,column,"",null,null,null,null,"60" );
        while (c.moveToNext())
        {
            String name =c.getString(0);
            String number = c.getString(1);
            Long _id = c.getLong(2);

            HashMap<String, String> element = new HashMap<>();
            element.put("Text1",name);
            element.put("Text2",number);
            element.put("id",_id.toString());
            _list.add(element);

        }
        return _list;
    }

    public Contacts selectByID (long id)
    {
        String[] column = {DataBaseManager.Constants.KEY_COL_ID,DataBaseManager.Constants.KEY_COL_name, DataBaseManager.Constants.KEY_COL_NUMBER};
        String selection = DataBaseManager.Constants.KEY_COL_ID + "=?";
        String[] selectionArg = new String[] { Long.toString(id) };
        Cursor c = mDb.query(DataBaseManager.Constants.myTable,column,selection,selectionArg,null,null,null,"60" );
        c.moveToNext();
        Contacts contact= new Contacts(c.getLong(0),c.getString(1),c.getString(2));
        return contact;
    }


    public List<HashMap<String, String>> select3 ()
    {
        List<HashMap<String, String>> _list = new ArrayList<HashMap<String, String>>() ;
        String[] column = {DataBaseManager.Constants.KEY_COL_name, DataBaseManager.Constants.KEY_COL_NUMBER, DataBaseManager.Constants.KEY_COL_ID};
        Cursor c = mDb.query(DataBaseManager.Constants.myTable,column,"",null,null,null,null,"3" );
        while (c.moveToNext())
        {
            String name =c.getString(0);
            String number = c.getString(1);
            Long _id = c.getLong(2);

            HashMap<String, String> element = new HashMap<>();
            element.put("Text1",name);
            element.put("Text2",number);
            element.put("id",_id.toString());
            _list.add(element);

        }
        return _list;
    }
}

