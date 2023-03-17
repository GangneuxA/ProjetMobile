package com.example.projetmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Form.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + DBContract.Form.TABLE_NAME + " (" +
                DBContract.Form._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.Form.COLUMN_QUESTION + " TEXT," +
                DBContract.Form.COLUMN_REPONSE + " TEXT);";

        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void insertQ(String question, String reponse){
        SQLiteDatabase db = this.getWritableDatabase();
        // insertion create a row and insert it
        ContentValues values = new ContentValues();
        values.put(DBContract.Form.COLUMN_QUESTION, question);
        values.put(DBContract.Form.COLUMN_REPONSE, reponse);
        db.insert(DBContract.Form.TABLE_NAME,null,values);
        db.close();
    }

    public List<String> selectAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> responses = new ArrayList<>();

        String[] projection = {
                DBContract.Form._ID,
                DBContract.Form.COLUMN_QUESTION,
                DBContract.Form.COLUMN_REPONSE

        };

        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Form._ID));
            String question =  cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_QUESTION));
            String reponse =  cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Form.COLUMN_REPONSE));
            String response = question;


            responses.add(response);
        }

        cursor.close();
        db.close();


        return responses;
    }
}
