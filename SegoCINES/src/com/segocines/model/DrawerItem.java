package com.segocines.model;


/*************************************************************/
/** @Author = ("Joaquin Casas", "Jon Casado")				**/
/*************************************************************/
///////////////////////////////////////////////////////////////
/* Contenido de cada item del panel NavigationDrawer. 		 */
///////////////////////////////////////////////////////////////
public class DrawerItem
{
	String ItemName;	//nombre	
    int imgResID;		//icono

    public DrawerItem(String itemName, int imgResID)
    {
          super();
          ItemName = itemName;
          this.imgResID = imgResID;
    }

    public String getItemName()
    {
          return ItemName;
    }
    
    public void setItemName(String itemName)
    {
          ItemName = itemName;
    }
    
    public int getImgResID()
    {
          return imgResID;
    }
    
    public void setImgResID(int imgResID)
    {
          this.imgResID = imgResID;
    }
}
