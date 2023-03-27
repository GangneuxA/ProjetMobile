package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Quizz extends AppCompatActivity {

    DBHandler db;
    LinearLayout ll ;
    public int num_rep;
    int valide;
    int nb_try;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        db = new DBHandler(this);
        ll = (LinearLayout) findViewById(R.id.ll);
        db.insertBasic();
        num_rep = Question();
        valide = 0;
        nb_try = 0;
    }


    public int Question(){
        TextView t = (TextView) findViewById(R.id.Question);
        List<QuestionReponse> result = db.QuestionDB();
        Random random = new Random();
        int i = (int) random.nextInt(result.size());
        t.setText(result.get(i).getQuestion());
        t.setBackgroundResource(R.color.purple_200);
        Button next = (Button) findViewById(R.id.Suivante);
        next.setVisibility(View.INVISIBLE);
        return i;

    }
    public void add(View v) throws InterruptedException {
        nb_try++;
        List<QuestionReponse> result = db.QuestionDB();
        EditText e = (EditText) findViewById(R.id.Reponse);
        String rep = e.getText().toString();
        TextView t = (TextView) findViewById(R.id.Question);
        String dbR = result.get(num_rep).getReponse();
        if(rep.toLowerCase(Locale.ROOT).equals(dbR.toLowerCase(Locale.ROOT))){
            t.setBackgroundResource(R.color.green);
            valide++;
            e.setText("");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            Button b = (Button) findViewById(R.id.valider);
            b.setVisibility(View.INVISIBLE);
            Button next = (Button) findViewById(R.id.Suivante);
            next.setVisibility(View.VISIBLE);
        }else{
            t.setBackgroundResource(R.color.warm);
            TextView TextviewRep = new TextView(this);
            TextviewRep.setText("the good answer is : "+dbR);
            ll.addView(TextviewRep);
        }
        TextView s = (TextView) findViewById(R.id.Score);
        s.setText("Score : "+valide+" / "+nb_try);
    }

    public void aff(View v) {

        num_rep = Question();
        Button b = (Button) findViewById(R.id.valider);
        b.setVisibility(View.VISIBLE);
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

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}