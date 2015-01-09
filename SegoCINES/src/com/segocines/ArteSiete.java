package com.segocines;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra la cartelera de los cines Artesiete. */
///////////////////////////////////////////////////////////////
public class ArteSiete extends ActionBarActivity
{	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artesiete);  
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	//MENUACTIONBAR
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{			
		switch(item.getItemId())
		{
			case R.id.action_Settings:
				NavUtils.navigateUpFromSameTask(this);
				startActivity(new Intent(this, PrefsActivity.class));
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	//FIN-MENUACTIONBAR
}
