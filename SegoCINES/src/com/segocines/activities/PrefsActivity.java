package com.segocines.activities;

import com.segocines.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;


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
}
