package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        saber = (ImageButton) findViewById(R.id.img);
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
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
                Intent i = new Intent(Lamp.this, Wiki.class);
                startActivity(i);
                return (true);
            case R.id.quizz:
                Intent i2 = new Intent(Lamp.this, Quizz.class);
                startActivity(i2);
                return (true);
            case R.id.lamp:
                Intent i3 = new Intent(Lamp.this, Lamp.class);
                startActivity(i3);
                return (true);
            case R.id.exit:
                finish();
                return (true); }
        return true; }

    public void flash(View v) throws CameraAccessException {
        RadioButton c2 = (RadioButton) findViewById(R.id.RGreen);
        RadioButton c3 = (RadioButton) findViewById(R.id.RPurple);
        RadioButton c4 = (RadioButton) findViewById(R.id.RRed);


        if(hasCameraFlash){
            if(flashON){
                flashON = false;
                saber.setImageResource(R.drawable.sabre_eteint);
                flashLightOff();
            }
            else{
                flashON = true;
                if(c2.isChecked()){
                    saber.setImageResource(R.drawable.sabre_vert);
                    flashLightOn();
                }else {
                    if (c3.isChecked()) {
                        saber.setImageResource(R.drawable.sabre_violet);
                        flashLightOn();
                    }else{
                        if(c4.isChecked()){
                            saber.setImageResource(R.drawable.sabre_rouge);
                            flashLightOn();
                        }else {
                            saber.setImageResource(R.drawable.sabre_bleu);
                            flashLightOn();
                        }
                    }
                }
            }
        }
    }

    private void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraID = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraID, true);
    }

    private void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraID = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraID, false);
    }
}