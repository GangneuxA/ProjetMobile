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

        String[] Questions={"What is the name of the first star wars movie ?", "What is the name of the yellow droid?","Who is Leia Organa's brother?",
                "What is the name of Han Solo's best friend?","What is the name of the creator of Star Wars","Who fight anakin on Mustafar?",
                "When was the first Star Wars movie released?","What is the name of Han Solo's starship?","Who is the creator of C3-PO?","What planet was Anakin born on?"};

        String[] Reponses={"A New Hope","C3-PO","Luke Skywalker","chewbacca","George Lucas","Obi-Wan Kenobi","1977","Millenium Falcon", "Anakin Skywalker","Tatooine"};

        for(int temp=0; temp<Questions.length;temp++){
            ContentValues values = new ContentValues();
            values.put(DBContract.Form.COLUMN_QUESTION,Questions[temp]);
            values.put(DBContract.Form.COLUMN_REPONSE,Reponses[temp]);
            db.insert(DBContract.Form.TABLE_NAME,null,values);
        }
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
