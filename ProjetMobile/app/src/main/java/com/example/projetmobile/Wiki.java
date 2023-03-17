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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;


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
        tResultat=(TextView) findViewById(R.id.tResultat);
        Wiki.RequestTask r=new Wiki.RequestTask();
        r.execute(req);
    }
    private class RequestTask extends AsyncTask<String, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
//  lance la requète
        String retour="";
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
                HttpsURLConnection connection = null;
                URL url=null;
                if (c1.isChecked()) {
                    url = new URL("https://swapi.dev/api/people/?search=" +
                            URLEncoder.encode(req,"utf-8"));
                }
                if (c2.isChecked()) {
                    url = new URL("https://swapi.dev/api/planets/?search=" +
                            URLEncoder.encode(req,"utf-8"));
                }
                if (c3.isChecked()) {
                    url = new URL("https://swapi.dev/api/starships/?search=" +
                            URLEncoder.encode(req,"utf-8"));
                }
                if (c4.isChecked()) {
                    url = new URL("https://swapi.dev/api/vehicles/?search=" +
                            URLEncoder.encode(req,"utf-8"));
                }
                if (c5.isChecked()) {
                    url = new URL("https://swapi.dev/api/species/?search=" +
                            URLEncoder.encode(req,"utf-8"));
                }
                if (c6.isChecked()){
                    url = new URL("https://swapi.dev/api/films/?search=" +
                            URLEncoder.encode(req,"utf-8"));
                }

                connection = (HttpsURLConnection) url.openConnection();
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

            if (c1.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("gender");
                    response += "\n" + jsoResult.getJSONObject(i).getString("birth_year");
                    response += "\n" + reqI(jsoResult.getJSONObject(i).getString("homeworld")) + "\n" ;

                }
            }
            if (c2.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("population") + "\n";
                }
            }
            if (c3.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("model");
                    response += "\n" + jsoResult.getJSONObject(i).getString("starship_class") + "\n";
                }
            }
            if (c4.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("model") + "\n";
                }
            }
            if (c5.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("language") + "\n";
                }
            }
            if (c6.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("title");
                    response += "\n" + jsoResult.getJSONObject(i).getString("director");
                    response += "\n" + jsoResult.getJSONObject(i).getString("release_date") + "\n";
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
        protected String reqI(String test) throws MalformedURLException, JSONException {
            String response2="";
            try {

                HttpsURLConnection connection2 = null;
                URL url2 = new URL(URLEncoder.encode(test,"utf-8"));
                connection2 = (HttpsURLConnection) url2.openConnection();
                connection2.setRequestMethod("GET");
                InputStream inputStream = connection2.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader);
                String ligne2 = bufferedReader2.readLine() ;

                while (ligne2!= null){
                    response2+=ligne2;
                    ligne2 = bufferedReader2.readLine();
                }

                JSONObject jso = new JSONObject(response2);
                JSONArray jsoResult2=jso.getJSONArray("results");
                for (int i = 0; i < jsoResult2.length(); i++) {
                    retour += jsoResult2.getJSONObject(i).getString("name");
                }

            } catch (UnsupportedEncodingException e) {
                response2 = "problème d'encodage";
            } catch (MalformedURLException e) {
                response2 = "problème d'URL ";
            } catch (IOException e) {
                response2 = "problème de connexion ";
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retour;
        }

    }
}