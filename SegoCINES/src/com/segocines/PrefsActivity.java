package com.segocines;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PrefsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener
{
	private SharedPreferences prefs;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		this.setPrefs(PreferenceManager.getDefaultSharedPreferences(this));
		this.getPrefs().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
    public void onSharedPreferenceChanged(SharedPreferences options, String key)
    {
		
    }
    
    public SharedPreferences getPrefs()
	{
		return prefs;
	}

	public void setPrefs(SharedPreferences prefs)
	{
		this.prefs = prefs;
	}
}
