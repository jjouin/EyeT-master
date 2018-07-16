package com.example.carla.eyetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemsDAO extends DAOBase{
    public ItemsDAO (Context pContext) {
        super(pContext);
    }

    public void insertItems(Items i)
    {
        ContentValues values = new ContentValues();
        values.put(DataBaseManager.Constants.KEY_COL_name, i.getName());
        values.put(DataBaseManager.Constants.KEY_COL_USE, 1);
        values.put(DataBaseManager.Constants.KEY_COL_IN, 1);
        mDb.insert(DataBaseManager.Constants.myTableItems,null,values);
    }

    public void deleteContact( Long id )
    {
        mDb.delete(DataBaseManager.Constants.myTableItems, DataBaseManager.Constants.KEY_COL_ID +"=?", new String[]{String.valueOf(id)});
    }

    public void upDateItems (Items i)
    {
        ContentValues values = new ContentValues();
        if (i.getIn()==0)
            values.put(DataBaseManager.Constants.KEY_COL_USE, i.getUse()+1);
        values.put(DataBaseManager.Constants.KEY_COL_IN, (i.getIn()+1)%2);
        mDb.update(DataBaseManager.Constants.myTableItems, values, DataBaseManager.Constants.KEY_COL_ID+"=?", new String[]{String.valueOf(i.getId())});
    }

    public void DeleteAllItems ()
    {
        ContentValues values = new ContentValues();
        values.put(DataBaseManager.Constants.KEY_COL_IN, 0);
        mDb.update(DataBaseManager.Constants.myTableItems, values, DataBaseManager.Constants.KEY_COL_IN+"=?", new String[]{String.valueOf(1)});
    }


    public Items selectByID (long id)
    {
        String[] column = {DataBaseManager.Constants.KEY_COL_ID,DataBaseManager.Constants.KEY_COL_name, DataBaseManager.Constants.KEY_COL_USE, DataBaseManager.Constants.KEY_COL_IN};
        String selection = DataBaseManager.Constants.KEY_COL_ID + "=?";
        String[] selectionArg = new String[] { Long.toString(id) };
        Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,selection,selectionArg,null,null,DataBaseManager.Constants.KEY_COL_USE,"60" );
        c.moveToNext();
        Items item= new Items(c.getLong(0),c.getString(1),c.getLong(2), c.getInt(3));
        return item;
    }

    public List<HashMap<String, String>> noSelectedList ()
    {
        List<HashMap<String, String>> _list = new ArrayList<HashMap<String, String>>() ;
        String[] column = {DataBaseManager.Constants.KEY_COL_name,DataBaseManager.Constants.KEY_COL_USE, DataBaseManager.Constants.KEY_COL_ID, DataBaseManager.Constants.KEY_COL_IN};
        String selection = DataBaseManager.Constants.KEY_COL_IN+ "=?";
        String[] selectionArg = new String[] {"0"};
        Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,selection,selectionArg,null,null,DataBaseManager.Constants.KEY_COL_USE,"60" );
        while (c.moveToNext())
        {
            String name =c.getString(0);
            Long use = c.getLong(1);
            Long _id = c.getLong(2);
            Boolean in = c.getInt(3)>0;

            HashMap<String, String> element = new HashMap<>();
            element.put("Text1",name);
            element.put("Text2",use.toString());
            element.put("id",_id.toString());
            element.put("in",in.toString() );
            _list.add(element);
        }
        return _list;
    }

    public List<HashMap<String, String>> selectedList ()
    {
        List<HashMap<String, String>> _list = new ArrayList<HashMap<String, String>>() ;
        String[] column = {DataBaseManager.Constants.KEY_COL_name,DataBaseManager.Constants.KEY_COL_USE, DataBaseManager.Constants.KEY_COL_ID, DataBaseManager.Constants.KEY_COL_IN};
        String selection = DataBaseManager.Constants.KEY_COL_IN+ "=?";
        String[] selectionArg = new String[] {"1"};
        Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,selection,selectionArg,null,null,DataBaseManager.Constants.KEY_COL_USE,"60" );
        while (c.moveToNext())
        {
            String name =c.getString(0);
            Long use = c.getLong(1);
            Long _id = c.getLong(2);
            Boolean in = c.getInt(3)>0;

            HashMap<String, String> element = new HashMap<>();
            element.put("Text1",name);
            element.put("Text2",use.toString());
            element.put("id",_id.toString());
            element.put("in",in.toString() );
            _list.add(element);
        }
        return _list;
    }



    public List<HashMap<String, String>> selectAll ()
    {
        List<HashMap<String, String>> _list = new ArrayList<HashMap<String, String>>() ;
        String[] column = {DataBaseManager.Constants.KEY_COL_name, DataBaseManager.Constants.KEY_COL_ID,DataBaseManager.Constants.KEY_COL_USE, DataBaseManager.Constants.KEY_COL_IN};
        Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,"",null,
                null,null,null,"200" );
        while (c.moveToNext())
        {
            String name =c.getString(0);

            HashMap<String, String> element = new HashMap<>();
            element.put("Text1",name);
            _list.add(element);
        }

        return _list;
    }
}
