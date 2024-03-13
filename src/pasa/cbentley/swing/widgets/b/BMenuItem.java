package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

public class BMenuItem extends JMenuItem implements IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -643480505038526391L;

   private int               commandID        = -1;

   private String            key;

   private Icon              normal;

   private SwingCtx          sc;

   public BMenuItem(SwingCtx sc, ActionListener al, String key) {
      this.sc = sc;
      this.key = key;
      this.addActionListener(al);
   }

   public void guiUpdate() {
      if (key != null) {
         this.setText(sc.getResString(key));
      }
      if (normal != null) {
         this.setIcon(normal);
      }
      sc.guiUpdateTooltip(this, key);
   }

   public void setIcon(String iconID, String iconCategory, int iconSize) {
      normal = sc.getResIcon(iconID, iconCategory, iconSize, IconFamily.ICON_MODE_0_DEFAULT);

   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BMenuItem");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BMenuItem");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

}
