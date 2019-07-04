package pasa.cbentley.swing.menu;

import javax.swing.JMenuItem;

public class ScreenMenuItem extends JMenuItem {

   
   private int type;
   private int screenID;

   public int getType() {
      return type;
   }

   public int getScreenID() {
      return screenID;
   }

   public ScreenMenuItem(String text, int type, int screenID) {
      super(text);
      this.type = type;
      this.screenID = screenID;
   }
   
}
