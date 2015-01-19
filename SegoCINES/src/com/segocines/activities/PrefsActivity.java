package com.segocines.activities;

import com.segocines.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra las preferencias compartidas.		 */
///////////////////////////////////////////////////////////////
public class PrefsActivity extends PreferenceActivity
{	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onBackPressed()
	{
		NavUtils.navigateUpFromSameTask(this);
	}
}
