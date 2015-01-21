package com.segocines.activities;

import com.segocines.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Activity que muestra las preferencias compartidas.		 */
///////////////////////////////////////////////////////////////
public class PrefsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener
{	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(getResources().getString(R.string.action_Settings));
	}
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onResume()
	{
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
	
    
    @SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
	{
    	//cambia el summary al cambiar las preferencias
    	Preference pref = findPreference(key);
        if(pref instanceof ListPreference)
        {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
            this.onContentChanged();
            return;
        }
	}
    
    @Override
	public void onContentChanged()
	{
        // put your code here
        super.onContentChanged();
    }
	
	@Override
	public void onBackPressed()
	{
		NavUtils.navigateUpFromSameTask(this);
	}
}
