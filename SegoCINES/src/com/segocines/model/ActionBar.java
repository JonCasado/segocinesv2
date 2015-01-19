package com.segocines.model;

import com.segocines.R;
import com.segocines.activities.PrefsActivity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Muestra el ActionBar. */
///////////////////////////////////////////////////////////////
public class ActionBar extends ActionBarActivity
{
	//MENUACTIONBAR
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public void onBackPressed()
	{
		NavUtils.navigateUpFromSameTask(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{		
		switch(item.getItemId())
		{
			//BACK
			case R.id.home:
				onBackPressed();
				return true;
		
			//AJUSTES
			case R.id.action_Settings:
				NavUtils.navigateUpFromSameTask(this);
				Intent intent = new Intent(this, PrefsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	//FIN-MENUACTIONBAR
}
