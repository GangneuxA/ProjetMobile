package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

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
import java.text.BreakIterator;

public class Wiki extends AppCompatActivity {

    TextView tResultat, iResultat;
    Button btn;
    EditText e;
    RadioButton c1,c2, c3, c4, c5, c6;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tResultat = (TextView) findViewById(R.id.tResultat);
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
                Intent i = new Intent(Wiki.this, Wiki.class);
                startActivity(i);
                return (true);
            case R.id.quizz:
                Intent i2 = new Intent(Wiki.this, Quizz.class);
                startActivity(i2);
                return (true);
            case R.id.lamp:
                Intent i3 = new Intent(Wiki.this, Lamp.class);
                startActivity(i3);
                return (true);
            case R.id.exit:
                finish();
                return (true); }
        return true; }

    public void go(View view) {
        e=(EditText) findViewById (R.id.recherche);
        String req =e.getText().toString();
        iResultat=(TextView) findViewById(R.id.iResultat);
        tResultat=(TextView) findViewById(R.id.tResultat);
        Wiki.RequestTask r=new Wiki.RequestTask();
        r.execute(req);
    }
    private class RequestTask extends AsyncTask<String, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
//  lance la requète

        protected String doInBackground(String... req) {
            String response = requete(req[0]);
            return response;
        }
        private String requete(String req) {
            c1 = (RadioButton) findViewById(R.id.Rpeople);
            c2 = (RadioButton) findViewById(R.id.Rplanets);
            c3 = (RadioButton) findViewById(R.id.Rship);
            c4 = (RadioButton) findViewById(R.id.Rvehicles);
            c5 = (RadioButton) findViewById(R.id.Rspecies);
            c6 = (RadioButton) findViewById(R.id.Rfilms);
            String response = "";
            try {
                HttpURLConnection connection = null;
                URL url=null;
                if (c1.isChecked()) {
                    url = new URL("https://swapi.dev/api/people/?search=" +
                            req);
                }
                if (c2.isChecked()) {
                    url = new URL("https://swapi.dev/api/planets/?search=" +
                            req);
                }
                if (c3.isChecked()) {
                    url = new URL("https://swapi.dev/api/starships/?search=" +
                            req);
                }
                if (c4.isChecked()) {
                    url = new URL("https://swapi.dev/api/vehicles/?search=" +
                            req);
                }
                if (c5.isChecked()) {
                    url = new URL("https://swapi.dev/api/species/?search=" +
                            req);
                }
                if (c6.isChecked()){
                    url = new URL("https://swapi.dev/api/films/?search=" +
                            req);
                }

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine() ;
                while (ligne!= null){
                    response+=ligne;
                    ligne = bufferedReader.readLine();
                }
            } catch (UnsupportedEncodingException e) {
                response = "problème d'encodage";
            } catch (MalformedURLException e) {
                response = "problème d'URL ";
            } catch (IOException e) {
                response = "problème de connexion ";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        private String decodeJSON(JSONObject jso) throws Exception {

            c1 = (RadioButton) findViewById(R.id.Rpeople);
            c2 = (RadioButton) findViewById(R.id.Rplanets);
            c3 = (RadioButton) findViewById(R.id.Rship);
            c4 = (RadioButton) findViewById(R.id.Rvehicles);
            c5 = (RadioButton) findViewById(R.id.Rspecies);
            c6 = (RadioButton) findViewById(R.id.Rfilms);
            String response = "";
            JSONArray jsoResult=jso.getJSONArray("results");
            //JSONArray jsoFilms=jso.getJSONArray("films");

            if (c1.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("gender");
                    response += "\n" + jsoResult.getJSONObject(i).getString("birth_year");
                    //RequestTaskIntern Ri=new RequestTaskIntern();
                    //Ri.execute(jsoResult.getJSONObject(i).getString("homeworld"));
                   // response += "\n" + iResultat.getText();
                    //response+="\n"+jsoResult.getJSONObject(i).getString("films");
                }
            }
            if (c2.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("population");
                }
            }
            if (c3.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("model");
                    response += "\n" + jsoResult.getJSONObject(i).getString("starship_class");
                }
            }
            if (c4.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("model");
                }
            }
            if (c5.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("language");
                }
            }
            if (c6.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("title");
                    response += "\n" + jsoResult.getJSONObject(i).getString("director");
                    response += "\n" + jsoResult.getJSONObject(i).getString("release_date");
                }
            }
            return response;

        }
        // Méthode appelée lorsque la tâche de fond sera terminée
        //  Affiche le résultat
        protected void onPostExecute(String result) {
            JSONObject toDecode = null;
            try {
                toDecode = new JSONObject(result);
                tResultat.setText(decodeJSON(toDecode));
            } catch (Exception e) {
                tResultat.setText("error parsing JSON");
            }
        }
    }

    private class RequestTaskIntern extends AsyncTask<String, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
//  lance la requète

        protected String doInBackground(String... req) {
            String response = requete(req[0]);
            return response;
        }
        private String requete(String req) {

            String response = "";
            try {
                HttpURLConnection connection = null;


                URL url = new URL(req);


                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine() ;
                while (ligne!= null){
                    response+=ligne;
                    ligne = bufferedReader.readLine();
                }
            } catch (UnsupportedEncodingException e) {
                response = "problème d'encodage";
            } catch (MalformedURLException e) {
                response = "problème d'URL ";
            } catch (IOException e) {
                response = "problème de connexion ";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        private String decodeJSON(JSONObject jso) throws Exception {

            String response = "";
            JSONArray jsoResult=jso.getJSONArray("results");

                for (int i = 0; i < jsoResult.length(); i++) {
                    response += jsoResult.getJSONObject(i).getString("name");
            }

            return response;

        }
        // Méthode appelée lorsque la tâche de fond sera terminée
        //  Affiche le résultat
        protected void onPostExecute(String result) {
            JSONObject toDecode = null;
            try {
                toDecode = new JSONObject(result);
                iResultat.setText(decodeJSON(toDecode));
            } catch (Exception e) {
                iResultat.setText("error parsing JSON");
            }
        }
    }

}