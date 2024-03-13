package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

public class BMenuItemToggle extends JMenuItem implements IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -643480505038526391L;

   private String            keyNormal;

   private SwingCtx          sc;

   private String            keySelected;

   public BMenuItemToggle(SwingCtx sc, ActionListener al) {
      this.sc = sc;
      this.addActionListener(al);
   }

   public void setTextKeys(String key, String keySelected) {
      this.keyNormal = key;
      this.keySelected = keySelected;
   }

   public void guiUpdate() {
      if (isSelected()) {
         if (keySelected != null) {
            this.setText(sc.getResString(keySelected));
            sc.guiUpdateTooltip(this, keySelected);
         }
      } else {
         if (keyNormal != null) {
            this.setText(sc.getResString(keyNormal));
            sc.guiUpdateTooltip(this, keyNormal);
         }
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BMenuItemToggle");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BMenuItemToggle");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

}
