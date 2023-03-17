package com.example.projetmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tResultat;
    Button btn;
    EditText e;
    RadioButton c1,c2, c3, c4, c5, c6;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        TextView txt;
        switch (item.getItemId()) {
            case R.id.wiki:
                Intent i = new Intent(MainActivity.this, Wiki.class);
                startActivity(i);
                return (true);
            case R.id.quizz:
                Intent i2 = new Intent(MainActivity.this, Quizz.class);
                startActivity(i2);
                return (true);
            case R.id.lamp:
                Intent i3 = new Intent(MainActivity.this, Lamp.class);
                startActivity(i3);
                return (true);
            case R.id.exit:
                finish();
                return (true); }
        return true; }

}