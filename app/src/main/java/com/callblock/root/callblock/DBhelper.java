package com.callblock.root.callblock;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 9/9/15.
 */
class DBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "blockno.db";
    public static final String TABLE_NAME = "numbers";
    public static final String COL_1 = "num";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL("Create table " + TABLE_NAME + "(num text");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertnumber(String number)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("num",number);
        long res= db.insert(TABLE_NAME,null,contentValues);
        if(res==-1)
            return false;
        else
            return true;
    }
}
