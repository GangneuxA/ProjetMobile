package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Quizz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
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
                Intent i = new Intent(Quizz.this, Wiki.class);
                startActivity(i);
                return (true);
            case R.id.quizz:
                Intent i2 = new Intent(Quizz.this, Quizz.class);
                startActivity(i2);
                return (true);
            case R.id.lamp:
                Intent i3 = new Intent(Quizz.this, Lamp.class);
                startActivity(i3);
                return (true);
            case R.id.exit:
                finish();
                return (true); }
        return true; }
}