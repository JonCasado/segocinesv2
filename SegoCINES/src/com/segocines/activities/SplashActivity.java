package com.segocines.activities;

import java.util.Timer;
import java.util.TimerTask;

import com.segocines.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
 

/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Pantalla inicial al cargar la aplicacion.				 */
///////////////////////////////////////////////////////////////
public class SplashActivity extends Activity
{
    //Duracion del Splash
    private static final long SPLASH_DELAY = 1500;
 
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
 
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	//orientacion vertical
        requestWindowFeature(Window.FEATURE_NO_TITLE);						//oculta el ActionBar
 
        setContentView(R.layout.splash);
 
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);	//lleva a la visualizacion de la cartelera general
 
                finish();
            }
        };
 
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);
    }
}
