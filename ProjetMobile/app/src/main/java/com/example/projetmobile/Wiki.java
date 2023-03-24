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
            try {
                JSONObject toDecode = new JSONObject(response);
                String enfin = decodeJSON(toDecode);
                return enfin;
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
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

                connection.disconnect();
                inputStream.close();
                inputStreamReader.close();

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
                    response += "\n" + reqI(jsoResult.getJSONObject(i).getString("homeworld"),1)  ;
                    JSONArray jsoFilms=jsoResult.getJSONObject(i).getJSONArray("films");
                    for (int j=0; j< jsoFilms.length();j++){
                        response += "\n" + reqI(jsoFilms.getString(j),2);
                    }
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
               // toDecode = new JSONObject(result);
                tResultat.setText(result);
            } catch (Exception e) {
                tResultat.setText("error parsing JSON");
            }

        }
        protected String reqI(String test, int id) throws MalformedURLException, JSONException {
            String response2="";
            try {

                HttpsURLConnection connection = null;
                URL url2 = new URL(test);

                connection = (HttpsURLConnection) url2.openConnection();

                InputStream inputStream2 = connection.getInputStream();

                InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream2);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader2);
                String ligne2 = bufferedReader.readLine() ;

                while (ligne2!= null){
                    response2+=ligne2;
                    ligne2 = bufferedReader.readLine();
                }


                JSONObject jso = new JSONObject(response2);

                if (id==1){
                    retour += jso.getString("name");
                }if(id==2){
                    retour = jso.getString("title");
                }

            } catch (UnsupportedEncodingException e) {
                retour = "problème d'encodage";
            } catch (MalformedURLException e) {
                retour = "problème d'URL ";
            } catch (IOException e) {
                retour = "problème de connexion ";
            } catch (Exception e) {
                retour="erreur";
                e.printStackTrace();
            }

            return retour;
        }


    }
}