package com.segocines.adapter;
 
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.segocines.R;
import com.segocines.app.ApplicationSegoCines;
import com.segocines.bd.BaseDeDatos;
import com.segocines.model.Pelicula;


 
public class CustomListAdapter extends BaseAdapter
{
    private Activity activity;
    private LayoutInflater inflater;
    //private BaseDeDatos BD;
    List<Pelicula> movieItems;
    //ImageLoader imageLoader = ApplicationSegoCines.getInstance().getImageLoader();
 
    public CustomListAdapter(Activity activity, List<Pelicula> movieList)
    {
        this.activity = activity;
        this.movieItems = movieList;
    }
 
    @Override
    public int getCount() {
        return movieItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    { 
        /*if (inflater == null)
        {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.formato_lista, null);
        }
 
        if (imageLoader == null)
        {
            imageLoader = ApplicationSegoCines.getInstance().getImageLoader();
        }
        
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.imgPeli);
        TextView title = (TextView) convertView.findViewById(R.id.nombrePeli);
        TextView year = (TextView) convertView.findViewById(R.id.anyoPeli);
 
        // getting movie data for the row
        Pelicula m = movieItems.get(position);
 
        // thumbnail image
        thumbNail.setImageUrl(m.getImg(), imageLoader);
         
        // title
        title.setText(m.getNombre());
         
        // release year
        year.setText(String.valueOf(m.getAnyo()));
 		*/
        return convertView;
    }
}
