package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Lamp extends AppCompatActivity {

    private ImageButton saber;
    boolean hasCameraFlash = false;
    boolean flashON = false;
    RadioButton c1,c2,c3,c4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.logo);
        saber = (ImageButton) findViewById(R.id.img);
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        c1 = (RadioButton) findViewById(R.id.RBlue);
        c2 = (RadioButton) findViewById(R.id.RGreen);
        c3 = (RadioButton) findViewById(R.id.RPurple);
        c4 = (RadioButton) findViewById(R.id.RRed);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Permet la navigation avec la toolBar
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView txt;
        switch (item.getItemId()) {
            case R.id.home:
                Intent i3 = new Intent(Lamp.this, MainActivity.class);
                finishAffinity();
                startActivity(i3);
                return (true);
            case R.id.wiki:
                Intent i = new Intent(Lamp.this, Wiki.class);
                finishAffinity();
                startActivity(i);
                return (true);
            case R.id.quizz:
                Intent i2 = new Intent(Lamp.this, Quizz.class);
                finishAffinity();
                startActivity(i2);
                return (true);
            /*case R.id.lamp:
                Intent i3 = new Intent(Lamp.this, Lamp.class);
                startActivity(i3);
                return (true);*/
            case R.id.exit:
                finish();
                return (true); }
        return true; }

    /**
     * Methode qui gére l'affichage des images selon si l'utilisateur veut allumer ou éteindre la lampe torche du téléphone
     * @param v
     * @throws CameraAccessException
     */
    public void flash(View v) throws CameraAccessException {
        MediaPlayer sound;
        if(hasCameraFlash){
            if(flashON){
                flashON = false;
                saber.setImageResource(R.drawable.sabre_eteint);
                flashLightOff();
                sound=MediaPlayer.create(this,R.raw.light_saber_off);
                sound.start();
            }
            else{
                flashON = true;
                sound=MediaPlayer.create(this,R.raw.light_saber_on);
                sound.start();
                if(c1.isChecked()){
                    saber.setImageResource(R.drawable.sabre_bleu);
                    flashLightOn();
                }
                if(c2.isChecked()){
                    saber.setImageResource(R.drawable.sabre_vert);
                    flashLightOn();
                }
                if (c3.isChecked()) {
                    saber.setImageResource(R.drawable.sabre_violet);
                    flashLightOn();
                }
                if(c4.isChecked()){
                    saber.setImageResource(R.drawable.sabre_rouge);
                    flashLightOn();
                }

            }
        }
    }

    /**
     * Methode permettant d'allumer la lampe torche du téléphone
     * @throws CameraAccessException
     */
    private void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraID = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraID, true);
    }

    /**
     * Methode permettant d'éteindre la lampe torche du téléphone
     * @throws CameraAccessException
     */
    private void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraID = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraID, false);
    }
}