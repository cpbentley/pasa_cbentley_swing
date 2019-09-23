package pasa.cbentley.swing.widgets.b;

import javax.swing.JMenu;
import javax.swing.event.MenuListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.menu.ITechMenu;

public class BMenu extends JMenu implements IMyGui, ITechMenu {

   private String           key;

   private MenuListener     lis;

   protected final SwingCtx sc;

   public BMenu(SwingCtx sc, MenuListener lis, String key) {
      this.sc = sc;
      this.lis = lis;
      this.key = key;
      if (lis != null) {
         this.addMenuListener(lis);
      }
   }

   public BMenu(SwingCtx sc, String key) {
      this(sc, null, key);
   }

   public void guiUpdate() {
      if (key != null) {
         this.setText(sc.getResString(key));
      }
      sc.guiUpdateTooltip(this, key);
      //since we are a container.. we must update below us
      sc.guiUpdateOnChildrenMenu(this);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BMenu");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("key", key);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BMenu");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
