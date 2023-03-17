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
    CheckBox c1,c2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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

    public void go(View view) {
        e=(EditText) findViewById (R.id.editVille);
        String req =e.getText().toString();
        tResultat=(TextView) findViewById(R.id.tResultat);
        RequestTask r=new RequestTask();
        r.execute(req);
        /*c1 = (CheckBox) findViewById(R.id.checkboxPeople);
        c2 = (CheckBox) findViewById(R.id.checkboxPlanet);

        if(c1.isChecked()){
            RequestTaskPeople r=new RequestTaskPeople();
            r.execute(req);
        }
        if(c2.isChecked()){
            RequestTaskPlanet r = new RequestTaskPlanet();
            r.execute(req);
        }*/

    }
    private class RequestTask extends AsyncTask<String, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
//  lance la requète

        protected String doInBackground(String... req) {
            String response = requete(req[0]);
            return response;
        }
        private String requete(String req) {
            c1 = (CheckBox) findViewById(R.id.checkboxPeople);
            c2 = (CheckBox) findViewById(R.id.checkboxPlanet);
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

            c1 = (CheckBox) findViewById(R.id.checkboxPeople);
            c2 = (CheckBox) findViewById(R.id.checkboxPlanet);
            String response = "";
            JSONArray jsoResult=jso.getJSONArray("results");

            if (c1.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("gender");
                    response += "\n" + jsoResult.getJSONObject(i).getString("birth_year");
                    //response+="\n"+jsoResult.getJSONObject(i).getString("homeworld");
                    //response+="\n"+jsoResult.getJSONObject(i).getString("films");
                }
            }
            if (c2.isChecked()) {
                for (int i = 0; i < jsoResult.length(); i++) {
                    response += "\n" + jsoResult.getJSONObject(i).getString("name");
                    response += "\n" + jsoResult.getJSONObject(i).getString("population");
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
}