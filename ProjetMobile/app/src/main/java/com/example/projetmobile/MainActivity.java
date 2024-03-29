package com.example.projetmobile;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
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

import androidx.appcompat.app.ActionBar;
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
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    TextView Res;
    int rdm;
    String requete;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.logo);
        Res=(TextView) findViewById(R.id.res);

        //Verifier si l'utilisateur est connecté à Internet
        if(isConnected()){
            rdm=(int)(Math.random()*6)+1;
            MainActivity.RequestTask r= new RequestTask();
            requete=""+rdm;
            r.execute(requete);
        }else{
            Res.setText("Veuillez activer votre connexion internet");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Methode pour verifier la connexion de l'utilisateur ( téléphone <= API 29 )
     * @return
     */
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean isConnected=false;
        for (Network network : connMgr.getAllNetworks()) {
            try{
                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
                if (networkInfo.isConnectedOrConnecting() && networkInfo!=null){
                    isConnected=true;
                }
            }catch (NullPointerException e){
                isConnected=false;
            }
        }
        return isConnected;
    }

    /**
     * Permet la navigation par la toolBar
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView txt;
        switch (item.getItemId()) {
            case R.id.wiki:
                Intent i = new Intent(MainActivity.this, Wiki.class);
                finishAffinity();
                startActivity(i);
                return (true);
            case R.id.quizz:
                Intent i2 = new Intent(MainActivity.this, Quizz.class);
                finishAffinity();
                startActivity(i2);
                return (true);
            case R.id.lamp:
                Intent i3 = new Intent(MainActivity.this, Lamp.class);
                finishAffinity();
                startActivity(i3);
                return (true);
            case R.id.exit:
                finish();
                return (true); }
        return true; }

    private class RequestTask extends AsyncTask<String, Void, String> {

        /**
         * lancé la requete lié a la demande
         * @param req
         * @return
         */
        protected String doInBackground(String... req) {
            String response = requete(req[0]);
            return response;
        }

        /**
         * Permet de récupérer le JSON de l'URL
         * @param req
         * @return
         */
        private String requete(String req) {
            String response = "";
            try {
                HttpURLConnection connection = null;
                URL url = new URL("https://swapi.dev/api/films/"+URLEncoder.encode(req,"utf-8")+"/");
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

        /**
         * Permet de faire le parsing du JSON récupérer
         * @param jso
         * @return
         * @throws Exception
         */
        private String decodeJSON(JSONObject jso) throws Exception {
            String response = "";
            response = jso.getString("opening_crawl");
            return response;
        }

        /**
         * Permet l'affichage du parsing du JSON
         * @param result
         */
        protected void onPostExecute(String result) {
            JSONObject toDecode = null;
            try {
                toDecode = new JSONObject(result);
                Res.setText(decodeJSON(toDecode));
            } catch (Exception e) {
                Res.setText("error parsing JSON");
            }
        }
    }
}