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

    public void insertBasic(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,2);
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();
        ContentValues values5 = new ContentValues();
        ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();
        ContentValues values8 = new ContentValues();
        ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();
        values1.put(DBContract.Form.COLUMN_QUESTION,"What is the name of the first star wars movie ?");
        values1.put(DBContract.Form.COLUMN_REPONSE,"A New Hope");
        values2.put(DBContract.Form.COLUMN_QUESTION,"What is the name of the yellow droid?");
        values2.put(DBContract.Form.COLUMN_REPONSE,"C3-PO");
        values3.put(DBContract.Form.COLUMN_QUESTION,"Who is Leia Organa's brother?");
        values3.put(DBContract.Form.COLUMN_REPONSE,"Luke Skywalker");
        values4.put(DBContract.Form.COLUMN_QUESTION,"What is the name of Han Solo's best friend?");
        values4.put(DBContract.Form.COLUMN_REPONSE,"chewbacca");
        values5.put(DBContract.Form.COLUMN_QUESTION,"What is the name of the creator of Star Wars");
        values5.put(DBContract.Form.COLUMN_REPONSE,"George Lucas");
        values6.put(DBContract.Form.COLUMN_QUESTION,"Who fight anakin on Mustafar?");
        values6.put(DBContract.Form.COLUMN_REPONSE,"Obi-Wan Kenobi");
        values7.put(DBContract.Form.COLUMN_QUESTION,"When was the first Star Wars movie released?");
        values7.put(DBContract.Form.COLUMN_REPONSE,"1977");
        values8.put(DBContract.Form.COLUMN_QUESTION,"What is the name of Han Solo's starship?");
        values8.put(DBContract.Form.COLUMN_REPONSE,"Millenium Falcon");
        values9.put(DBContract.Form.COLUMN_QUESTION,"Who is the creator of C3-PO?");
        values9.put(DBContract.Form.COLUMN_REPONSE,"Anakin Skywalker");
        values10.put(DBContract.Form.COLUMN_QUESTION,"What planet was Anakin born on?");
        values10.put(DBContract.Form.COLUMN_REPONSE,"Tatooine");
        db.insert(DBContract.Form.TABLE_NAME,null,values1);
        db.insert(DBContract.Form.TABLE_NAME,null,values2);
        db.insert(DBContract.Form.TABLE_NAME,null,values3);
        db.insert(DBContract.Form.TABLE_NAME,null,values4);
        db.insert(DBContract.Form.TABLE_NAME,null,values5);
        db.insert(DBContract.Form.TABLE_NAME,null,values6);
        db.insert(DBContract.Form.TABLE_NAME,null,values7);
        db.insert(DBContract.Form.TABLE_NAME,null,values8);
        db.insert(DBContract.Form.TABLE_NAME,null,values9);
        db.insert(DBContract.Form.TABLE_NAME,null,values10);
        db.close();
    }

    public List<QuestionReponse> QuestionDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<QuestionReponse> responses = new ArrayList<>();
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
            QuestionReponse result = new QuestionReponse(id,question,reponse);


            responses.add(result);
        }

        cursor.close();
        db.close();


        return responses;


    }

   /* public void insertQ(String question, String reponse){
        SQLiteDatabase db = this.getWritableDatabase();
        // insertion create a row and insert it
        ContentValues values = new ContentValues();
        values.put(DBContract.Form.COLUMN_QUESTION, question);
        values.put(DBContract.Form.COLUMN_REPONSE, reponse);
        db.insert(DBContract.Form.TABLE_NAME,null,values);
        db.close();
    }
*/
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
