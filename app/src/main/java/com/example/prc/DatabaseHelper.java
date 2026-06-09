package com.example.prc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "personalIdentity.db";
    public static final String TABLE = "IDENTITY";
    public static final String C1 = "ID";
    public static final String C2 = "FIRSTNAME";
    public static final String C3 = "LASTNAME";
    public static final String C4 = "MI";
    public static final String C5 = "DATEOFBIRTH";
    public static final String C6 = "GENDER";
    public static final String C7 = "ADDRESS";
    public static final String C8 = "CITY";
    public static final String C9 = "RELATIONSHIP";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE + " ("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "FIRSTNAME TEXT, "+
                "LASTNAME TEXT, "+
                "MI TEXT, "+
                "DATEOFBIRTH TEXT, "+
                "GENDER TEXT, "+
                "ADDRESS TEXT, "+
                "CITY TEXT, "+
                "RELATIONSHIP TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE );
        onCreate(db);
    }

    //insert data
    public Boolean insertData(String name, String lastName, String mi, String dateofbirth,
                              String gender, String address, String city, String relationship){

        try(SQLiteDatabase db = this.getWritableDatabase()){
            ContentValues cv = new ContentValues();
            cv.put(C2, name);
            cv.put(C3, lastName);
            cv.put(C4, mi);
            cv.put(C5, dateofbirth);
            cv.put(C6, gender);
            cv.put(C7, address);
            cv.put(C8, city);
            cv.put(C9, relationship);

            long result = db.insert(TABLE, null, cv);
            return result != -1;
        }catch (Exception e){
            return false;
        }

    }

    //addAllData
    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " +TABLE, null);
    }

}
