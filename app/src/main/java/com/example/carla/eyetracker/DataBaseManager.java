package com.example.carla.eyetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.example.carla.eyetracker.DataBaseManager.Constants._datBaseVersion;
import static com.example.carla.eyetracker.DataBaseManager.Constants._dataBaseName;
import static com.example.carla.eyetracker.DataBaseManager.Constants.myTableItems;

public class DataBaseManager extends SQLiteOpenHelper {

    public static class Constants implements BaseColumns {
        public static final String _dataBaseName="db";
        public static final int _datBaseVersion=4;
        public static final String myTable ="T_contacts";
        public static final String myTableItems ="T_Items";
        public static final String KEY_COL_ID = "_id";
        public static final String KEY_COL_name = "name";
        public static final String KEY_COL_NUMBER = "numberPhone";
        public static final String KEY_COL_IN = "selected ";
        public static final String KEY_COL_USE = "use";
        public static final int ID_COLUMN=1;
        public static final int NAME_COLUMN=2;
        public static final int NUMBER_COLUMN=3;

    }

    private static final String DATABASE_CREATE="create TABLE "
            +Constants.myTable+ "("+ Constants.KEY_COL_ID
            +" integer primary key autoincrement, "+ Constants.KEY_COL_name
            +" TEXT, " + Constants.KEY_COL_NUMBER +" TEXT ) ";


    private static final String DATABASE_CREATE_ITEMS= "create TABLE "
            + myTableItems+ "( "
            + Constants.KEY_COL_ID +" integer primary key autoincrement, "
            + Constants.KEY_COL_name +" TEXT, "
            +Constants.KEY_COL_USE +" integer default 1, "
            + Constants.KEY_COL_IN +" integer default 0) ";


    public DataBaseManager(Context context) {
        super(context, _dataBaseName, null, _datBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE_ITEMS);
        db.execSQL("insert into "+myTableItems+" ("+Constants.KEY_COL_name+") values ('Tomatos')");
        db.execSQL("insert into "+myTableItems+" ("+Constants.KEY_COL_name+") values ('Eggs')");
        db.execSQL("insert into "+myTableItems+" ("+Constants.KEY_COL_name+") values ('Milk')");
        db.execSQL("insert into "+myTableItems+" ("+Constants.KEY_COL_name+") values ('Bread')");
        db.execSQL("insert into "+myTableItems+" ("+Constants.KEY_COL_name+") values ('Toilet paper')");
        db.execSQL("insert into "+myTableItems+" ("+Constants.KEY_COL_name+") values ('Toothpaste')");
        db.execSQL("insert into "+myTableItems+" ("+Constants.KEY_COL_name+") values ('Coffee')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String TABLE_DROP = "DROP TABLE IF EXISTS T_contacts ";
        db.execSQL(TABLE_DROP);
        String TABLE_DROP_i = "DROP TABLE IF EXISTS T_Items ";
        db.execSQL(TABLE_DROP_i);
        onCreate(db);
    }
}
