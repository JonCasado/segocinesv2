package com.segocines.model;

import com.segocines.R;
import com.segocines.activities.PrefsActivity;

import android.content.Intent;
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
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{		
		switch(item.getItemId())
		{
			//AJUSTES
			case R.id.action_Settings:
				Intent intent = new Intent(this, PrefsActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	//FIN-MENUACTIONBAR
}
