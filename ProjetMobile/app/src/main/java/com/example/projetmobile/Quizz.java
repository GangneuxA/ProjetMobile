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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Quizz extends AppCompatActivity {

    DBHandler db;
    public int num_rep;
    int valide;
    int nb_try;
    ArrayList<Integer> q = new ArrayList<Integer>();
    Button r , next, valid;
    TextView ques;
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.logo);
        db = new DBHandler(this);
        db.insertBasic();
        valide = 0;
        nb_try = 0;
        r = (Button) findViewById(R.id.rejouer);
        r.setVisibility(View.INVISIBLE);
        ques = (TextView) findViewById(R.id.Question);
        next = (Button) findViewById(R.id.Suivante);
        valid = (Button) findViewById(R.id.valider);
        e = (EditText) findViewById(R.id.Reponse);
        num_rep = Question();
    }


    /**
     * Methode permettant de génerer une question de la BDD
     * @return
     */
    public int Question(){
        boolean b = false;
        List<QuestionReponse> result = db.QuestionDB();
        Random random = new Random();
        int i = (int) random.nextInt(result.size());
        if(!q.isEmpty()){
            while(!b){
                if(!q.contains(i)){
                    b = true;
                }else{
                    i = (int) random.nextInt(result.size());
                }
            }
        }
        q.add(i);
        ques.setText(result.get(i).getQuestion());
        ques.setBackgroundResource(R.color.purple_200);
        next.setVisibility(View.INVISIBLE);
        return i;

    }

    /**
     * Methode permettant de vérifier la reponse de la BDD avec la reponse rentré par l'utilisateur
     * @param v
     * @throws InterruptedException
     */
    public void valide(View v) throws InterruptedException {
        nb_try++;
        List<QuestionReponse> result = db.QuestionDB();
        String rep = e.getText().toString();
        String dbR = result.get(num_rep).getReponse();
        if(rep.toLowerCase(Locale.ROOT).equals(dbR.toLowerCase(Locale.ROOT))){
            ques.setBackgroundResource(R.color.green);
            valide++;
            e.setText("");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            Button b = (Button) findViewById(R.id.valider);
            b.setVisibility(View.INVISIBLE);
            Button next = (Button) findViewById(R.id.Suivante);
            next.setVisibility(View.VISIBLE);
        }else{
            ques.setBackgroundResource(R.color.warm);
            Button next = (Button) findViewById(R.id.Suivante);
            next.setVisibility(View.VISIBLE);
            e.setText("");
        }
        TextView s = (TextView) findViewById(R.id.Score);
        s.setText("Score : "+valide+" / "+nb_try);
    }

    /**
     * Permet l'affichage des boutons selon la situation du quizz
     * @param v
     */
    public void aff(View v) {
        if(q.size()!=10){
            num_rep = Question();
            valid.setVisibility(View.VISIBLE);
        }else{
            r.setVisibility(View.VISIBLE);
            ques.setBackgroundResource(R.color.green);
            ques.setText("Quizz is finish, good job and try again for a better score !");
            valid.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Methode permettant de relancer un quizz
     * @param v
     */
    public void rejouer(View v){
        Intent i = new Intent(Quizz.this, Quizz.class);
        finishAffinity();
        startActivity(i);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    /**
     * Permet la navigation sur la toolBar
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView txt;
        switch (item.getItemId()) {
            case R.id.home:
                Intent i2 = new Intent(Quizz.this, MainActivity.class);
                finishAffinity();
                startActivity(i2);
                return (true);
            case R.id.wiki:
                Intent i = new Intent(Quizz.this, Wiki.class);
                finishAffinity();
                startActivity(i);
                return (true);
            /*case R.id.quizz:
                Intent i2 = new Intent(Quizz.this, Quizz.class);
                startActivity(i2);
                return (true);*/
            case R.id.lamp:
                Intent i3 = new Intent(Quizz.this, Lamp.class);
                finishAffinity();
                startActivity(i3);
                return (true);
            case R.id.exit:
                finish();
                return (true); }
        return true; }

    /**
     * Permet la fermeture de la BDD
     */
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}