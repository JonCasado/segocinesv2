package com.segocines.adapter;

import java.util.List;

import com.segocines.R;
import com.segocines.model.DrawerItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 

/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* A�ade la lista de items al panel NavigationDrawer. 		 */
///////////////////////////////////////////////////////////////
public class DrawerAdapter extends ArrayAdapter<DrawerItem>
{
      Context context;
      List<DrawerItem> drawerItemList;
      int layoutResID;
 
      public DrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems)
      {
            super(context, layoutResourceID, listItems);
            this.context = context;
            this.drawerItemList = listItems;
            this.layoutResID = layoutResourceID;
      }
 
      @Override
      public View getView(int position, View convertView, ViewGroup parent)
      {
            DrawerItemHolder drawerHolder;
            View view = convertView;
 
            if (view == null)
            {
                  LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                  drawerHolder = new DrawerItemHolder();
 
                  view = inflater.inflate(layoutResID, parent, false);
                  drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
                  drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
 
                  view.setTag(drawerHolder);
            }
            else
            {
                  drawerHolder = (DrawerItemHolder) view.getTag();
            }
 
            DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
 
            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgResID()));	//icono
            drawerHolder.ItemName.setText(dItem.getItemName());											//texto
 
            return view;
      }
 
      private static class DrawerItemHolder
      {
            TextView ItemName;
            ImageView icon;
      }
}
