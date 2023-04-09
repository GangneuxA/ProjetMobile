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
    EditText e;
    String lien;
    RadioButton c1,c2, c3, c4, c5, c6;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.logo);

        tResultat = (TextView) findViewById(R.id.tResultat);
        e=(EditText) findViewById (R.id.recherche);

        c1 = (RadioButton) findViewById(R.id.Rpeople);
        c2 = (RadioButton) findViewById(R.id.Rplanets);
        c3 = (RadioButton) findViewById(R.id.Rship);
        c4 = (RadioButton) findViewById(R.id.Rvehicles);
        c5 = (RadioButton) findViewById(R.id.Rspecies);
        c6 = (RadioButton) findViewById(R.id.Rfilms);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Permet la navigation par la toolBar
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView txt;
        switch (item.getItemId()) {
            case R.id.home:
                Intent i = new Intent(Wiki.this, MainActivity.class);
                finishAffinity();
                startActivity(i);
                return (true);
            case R.id.quizz:
                Intent i2 = new Intent(Wiki.this, Quizz.class);
                finishAffinity();
                startActivity(i2);
                return (true);
            case R.id.lamp:
                Intent i3 = new Intent(Wiki.this, Lamp.class);
                finishAffinity();
                startActivity(i3);
                return (true);
            case R.id.exit:
                finish();
                return (true); }
        return true; }

    /**
     * Permet de récupérer la valeur inscrite dans l'editText par l'utilisateur et ainsi lancé la requete de recherche
     * @param view
     */
    public void go(View view) {
        String req =e.getText().toString();
        Wiki.RequestTask r=new Wiki.RequestTask();
        r.execute(req);
    }



    private class RequestTask extends AsyncTask<String, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
//  lance la requète
        String retour="";
        /**
         * lancé la requete lié a la demande
         * @param req
         * @return
         */
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
        /**
         * Permet de récupérer le JSON de l'URL
         * @param req
         * @return
         */
        private String requete(String req) {
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

        /**
         * Permet de faire le parsing du JSON récupérer
         * @param jso
         * @return
         * @throws Exception
         */
        private String decodeJSON(JSONObject jso) throws Exception {
            String response = "";
            JSONArray jsoResult=jso.getJSONArray("results");

            if (c1.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    lien=jsoResult.getJSONObject(i).getString("url");
                    response += "\nName: " + jsoResult.getJSONObject(i).getString("name");
                    response += "\nGender: " + jsoResult.getJSONObject(i).getString("gender");

                    JSONArray jsoSpe=jsoResult.getJSONObject(i).getJSONArray("species");
                    for (int j=0; j< jsoSpe.length();j++){
                        if(!jsoSpe.isNull(j)){
                            response += "\nSpecies: "+reqI(jsoSpe.getString(j),1);
                        }
                    }
                    response += "\nHeight: " + jsoResult.getJSONObject(i).getString("height")+"    Mass: "+ jsoResult.getJSONObject(i).getString("mass");
                    response += "\nBirth year: " + jsoResult.getJSONObject(i).getString("birth_year");
                    response += "\nHomeworld: " + reqI(jsoResult.getJSONObject(i).getString("homeworld"),1)  ;

                    JSONArray jsoVeh=jsoResult.getJSONObject(i).getJSONArray("vehicles");
                    response+="\n\nVehicles: ";
                    if(!jsoVeh.isNull(0)) {
                        for (int j = 0; j < jsoVeh.length(); j++) {

                            response += "\n    -" + reqI(jsoVeh.getString(j), 1);
                        }
                    }else{
                            response+="\nThis character doesn't have his own vehicle";
                    }

                    JSONArray jsoSta=jsoResult.getJSONObject(i).getJSONArray("starships");
                    response+="\n\nStarships: ";
                    if(!jsoSta.isNull(0)){
                        for (int j=0; j< jsoSta.length();j++) {
                            response += "\n    -" + reqI(jsoSta.getString(j), 1);
                        }
                    }else{
                       response+= "\nThis character doesn't have his own starship";
                    }

                    JSONArray jsoFilms=jsoResult.getJSONObject(i).getJSONArray("films");
                    response+= "\n\nAppearances: ";
                    for (int j=0; j< jsoFilms.length();j++){
                        response += "\n    -" + reqI(jsoFilms.getString(j),2);
                    }
                    response+="\n";
                }
            }
            if (c2.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    lien=jsoResult.getJSONObject(i).getString("url");
                    response += "\nName: " + jsoResult.getJSONObject(i).getString("name");
                    response += "\nPopulation: " + jsoResult.getJSONObject(i).getString("population") + " peoples";
                    response += "\nClimate: " + jsoResult.getJSONObject(i).getString("climate");
                    response += "\nTerrain: " + jsoResult.getJSONObject(i).getString("terrain");
                    response += "\nDiameter: "+jsoResult.getJSONObject(i).getString("diameter");
                    response += "\nRotation period: "+jsoResult.getJSONObject(i).getString("rotation_period");
                    response += "\nOrbital period: "+jsoResult.getJSONObject(i).getString("orbital_period");
                    response += "\nGravity: "+jsoResult.getJSONObject(i).getString("gravity");

                    JSONArray jsoRes=jsoResult.getJSONObject(i).getJSONArray("residents");
                    response+="\n\nResidents: ";
                    if(!jsoRes.isNull(0)){
                        for (int j=0; j< jsoRes.length();j++) {
                            response += "\n    -" + reqI(jsoRes.getString(j), 1);
                        }
                    }else{
                        response+= "\nThis planet doesn't have any residents";
                    }

                    JSONArray jsoFilms=jsoResult.getJSONObject(i).getJSONArray("films");
                    response+= "\n\nAppearances: ";
                    for (int j=0; j< jsoFilms.length();j++){
                        response += "\n    -" + reqI(jsoFilms.getString(j),2);
                    }
                    response+="\n";
                }
            }
            if (c3.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    lien=jsoResult.getJSONObject(i).getString("url");
                    response += "\nName: " + jsoResult.getJSONObject(i).getString("name");
                    response += "\nModel: " + jsoResult.getJSONObject(i).getString("model");
                    response += "\nManufacturer: " + jsoResult.getJSONObject(i).getString("manufacturer") ;
                    response += "\nCost: " + jsoResult.getJSONObject(i).getString("cost_in_credits")+" credits";
                    response += "\nLength: " + jsoResult.getJSONObject(i).getString("length");
                    response += "\nCrew: " + jsoResult.getJSONObject(i).getString("crew");
                    response += "\nPassengers: " + jsoResult.getJSONObject(i).getString("passengers");
                    response += "\nCargo capacity" + jsoResult.getJSONObject(i).getString("cargo_capacity");
                    response += "\nConsumables: " + jsoResult.getJSONObject(i).getString("consumables");

                    JSONArray jsoPil=jsoResult.getJSONObject(i).getJSONArray("pilots");
                    response+="\n\npilots: ";
                    if(!jsoPil.isNull(0)){
                        for (int j=0; j< jsoPil.length();j++) {
                            response += "\n    -" + reqI(jsoPil.getString(j), 1);
                        }
                    }else{
                        response+= "\nThis planet doesn't have any pilots";
                    }

                    JSONArray jsoFilms=jsoResult.getJSONObject(i).getJSONArray("films");
                    response+= "\n\nAppearances: ";
                    for (int j=0; j< jsoFilms.length();j++){
                        response += "\n    -" + reqI(jsoFilms.getString(j),2);
                    }
                    response+="\n";
                }
            }
            if (c4.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    lien=jsoResult.getJSONObject(i).getString("url");
                    response += "\nName: " + jsoResult.getJSONObject(i).getString("name");
                    response += "\nModel: " + jsoResult.getJSONObject(i).getString("model");
                    response += "\nManufacturer: " + jsoResult.getJSONObject(i).getString("manufacturer") ;
                    response += "\nCost: " + jsoResult.getJSONObject(i).getString("cost_in_credits")+" credits";
                    response += "\nLength: " + jsoResult.getJSONObject(i).getString("length");
                    response += "\nCrew: " + jsoResult.getJSONObject(i).getString("crew");
                    response += "\nPassengers: " + jsoResult.getJSONObject(i).getString("passengers");
                    response += "\nCargo capacity" + jsoResult.getJSONObject(i).getString("cargo_capacity");
                    response += "\nConsumables: " + jsoResult.getJSONObject(i).getString("consumables");

                    JSONArray jsoPil=jsoResult.getJSONObject(i).getJSONArray("pilots");
                    response+="\n\npilots: ";
                    if(!jsoPil.isNull(0)){
                        for (int j=0; j< jsoPil.length();j++) {
                            response += "\n    -" + reqI(jsoPil.getString(j), 1);
                        }
                    }else{
                        response+= "\nThis planet doesn't have any pilots";
                    }

                    JSONArray jsoFilms=jsoResult.getJSONObject(i).getJSONArray("films");
                    response+= "\n\nAppearances: ";
                    for (int j=0; j< jsoFilms.length();j++){
                        response += "\n    -" + reqI(jsoFilms.getString(j),2);
                    }
                    response+="\n";
                }
            }
            if (c5.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    lien=jsoResult.getJSONObject(i).getString("url");
                    response += "\nName: " + jsoResult.getJSONObject(i).getString("name");
                    response += "\nHomeworld: " + reqI(jsoResult.getJSONObject(i).getString("homeworld"),1);
                    response += "\nLanguage: " + jsoResult.getJSONObject(i).getString("language");

                    response += "\nClassification: " + jsoResult.getJSONObject(i).getString("classification");
                    response += "\nDesignation: " + jsoResult.getJSONObject(i).getString("designation");
                    response += "\nAverage lifespan: " + jsoResult.getJSONObject(i).getString("average_lifespan");

                    response += "\nAverage height: " + jsoResult.getJSONObject(i).getString("average_height");
                    response += "\nSkin Colors: " + jsoResult.getJSONObject(i).getString("skin_colors");
                    response += "\nHair colors: " + jsoResult.getJSONObject(i).getString("hair_colors");
                    response += "\nEyes colors: " + jsoResult.getJSONObject(i).getString("eye_colors");

                    JSONArray jsoPeo=jsoResult.getJSONObject(i).getJSONArray("people");
                    response+="\n\nPeoples: ";
                    if(!jsoPeo.isNull(0)){
                        for (int j=0; j< jsoPeo.length();j++) {
                            response += "\n    -" + reqI(jsoPeo.getString(j), 1);
                        }
                    }else{
                        response+= "\nThis species doesn't have any famous people";
                    }

                    JSONArray jsoFilms=jsoResult.getJSONObject(i).getJSONArray("films");
                    response+= "\n\nAppearances: ";
                    for (int j=0; j< jsoFilms.length();j++){
                        response += "\n    -" + reqI(jsoFilms.getString(j),2);
                    }
                    response+="\n";
                }
            }
            if (c6.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    lien=jsoResult.getJSONObject(i).getString("url");
                    response += "\nTitle: " + jsoResult.getJSONObject(i).getString("title");
                    response += "\nRelease date: " + jsoResult.getJSONObject(i).getString("release_date");
                    response += "\nDirector: " + jsoResult.getJSONObject(i).getString("director");
                    response += "\nProducer: " + jsoResult.getJSONObject(i).getString("producer");

                    JSONArray jsoCha=jsoResult.getJSONObject(i).getJSONArray("characters");
                    response+="\n\nCharacters: ";
                    for (int j=0; j< jsoCha.length();j++) {
                        response += "\n    -" + reqI(jsoCha.getString(j), 1);
                    }
                    JSONArray jsoPla=jsoResult.getJSONObject(i).getJSONArray("planets");
                    response+="\n\nPlanets: ";
                    for (int j=0; j< jsoPla.length();j++) {
                        response += "\n    -" + reqI(jsoPla.getString(j), 1);
                    }
                    JSONArray jsoSta=jsoResult.getJSONObject(i).getJSONArray("starships");
                    response+="\n\nStarships: ";
                    for (int j=0; j< jsoSta.length();j++) {
                        response += "\n    -" + reqI(jsoSta.getString(j), 1);
                    }
                    JSONArray jsoVeh=jsoResult.getJSONObject(i).getJSONArray("vehicles");
                    response+="\n\nVehicles: ";
                    if(!jsoVeh.isNull(0)) {
                        for (int j = 0; j < jsoVeh.length(); j++) {

                            response += "\n    -" + reqI(jsoVeh.getString(j), 1);
                        }
                    }else{
                        response+="\n";
                    }
                    JSONArray jsoSpe=jsoResult.getJSONObject(i).getJSONArray("species");
                    response+="\n\nSpecies: ";
                    for (int j=0; j< jsoSpe.length();j++) {
                        response += "\n    -" + reqI(jsoSpe.getString(j), 1);
                    }
                    response+="\n";
                }
            }
            return response;
        }

        /**
         * Affichage du résultat de la requete
         * @param result
         */
        protected void onPostExecute(String result) {
            JSONObject toDecode = null;
            try {
                tResultat.setText(result);
            } catch (Exception e) {
                tResultat.setText("error parsing JSON");
            }

        }

        /**
         * Class permettant de pouvoir gérer les sous-requetes dans la requete principale
         * @param req
         * @param id
         * @return
         * @throws MalformedURLException
         * @throws JSONException
         */
        protected String reqI(String req, int id) throws MalformedURLException, JSONException {
            String response2="";
            try {

                HttpsURLConnection connection = null;
                URL url2 = new URL(req);

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
                    retour = jso.getString("name");
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