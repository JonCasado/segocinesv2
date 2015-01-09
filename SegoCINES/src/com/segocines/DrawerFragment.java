package com.segocines;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class DrawerFragment extends Fragment
{ 
    public final static String KEY_TEXT = "key_text";
 
    private String mText;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mText = getArguments().getString(KEY_TEXT);
 
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        
        TextView txtV = (TextView) v.findViewById(R.id.frag1_text);
        txtV.setText(mText);
 
        return v;
    }
}
