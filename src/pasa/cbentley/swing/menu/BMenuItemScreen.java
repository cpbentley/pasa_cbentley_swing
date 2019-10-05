package pasa.cbentley.swing.menu;

import java.awt.event.ActionListener;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BMenuItem;

public class BMenuItemScreen extends BMenuItem {

   /**
    * 
    */
   private static final long serialVersionUID = -9154133633403793046L;

   private int               screenID;

   private int               type;

   public BMenuItemScreen(SwingCtx sc, ActionListener al, String key, int type, int screenID) {
      super(sc, al, key);
      this.type = type;
      this.screenID = screenID;
   }

   public int getScreenID() {
      return screenID;
   }

   public int getType() {
      return type;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "BMenuItemScreen");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BMenuItemScreen");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
