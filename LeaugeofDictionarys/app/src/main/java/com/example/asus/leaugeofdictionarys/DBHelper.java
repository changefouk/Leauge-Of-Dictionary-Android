package com.example.asus.leaugeofdictionarys;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //Database name and version
    public static final String DATABASE_NAME = "lexitron";
    public static final int DATABASE_VERSION = 1;

    //Table name and Structure information
    public static final String CONTACTS_TABLE_NAME = "eng2thai";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_ESEARCH = "esearch";
    public static final String CONTACTS_COLUMN_EENTRY = "eentry";
    public static final String CONTACTS_COLUMN_TENTRY = "tentry";
    public static final String CONTACTS_COLUMN_ECAT = "ecat";
    public static final String CONTACTS_COLUMN_ETHAI = "ethai";
    public static final String CONTACTS_COLUMN_ESYN = "esyn";
    public static final String CONTACTS_COLUMN_EANT = "eant";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME +
                        "( " + CONTACTS_COLUMN_ID + " integer primary key autoincrement, " +
                        CONTACTS_COLUMN_ESEARCH + " text, " +
                        CONTACTS_COLUMN_EENTRY + " text, " +
                        CONTACTS_COLUMN_TENTRY + " text, " +
                        CONTACTS_COLUMN_ECAT + " text, " +
                        CONTACTS_COLUMN_ETHAI + " text, " +
                        CONTACTS_COLUMN_ESYN + " text, " +
                        CONTACTS_COLUMN_EANT + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS eng2thai");
        onCreate(db);
    }

   // public int numberOfRows(){
    //    SQLiteDatabase db = this.getReadableDatabase();
    //    int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
   //     return numRows;
   // }


    //Cursor getdata_2
    //public Cursor getData_2(String eentryData){
      //  SQLiteDatabase db = this.getReadableDatabase();
      //  Cursor res_2 = db.rawQuery("select * from eng2thai where eentry=" + "'" + eentryData + "'" + "limit 1" + "", null);
     //   return res_2;
   // }

    //ArrayList getContacts_2
    public ArrayList<String> getAllcontacts_2(){
        ArrayList<String> array_list_2 = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res_2 = db.rawQuery( "select distinct eentry from eng2thai", null);
        res_2.moveToFirst();

        while (res_2.isAfterLast() == false){
            array_list_2.add(res_2.getString(res_2.getColumnIndex(CONTACTS_COLUMN_EENTRY)));
            res_2.moveToNext();
        }
        return array_list_2;
    }
}
