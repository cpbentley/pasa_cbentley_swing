package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

public class BCheckBox extends JCheckBox implements IMyGui {

   private SwingCtx sc;

   private String   key;

   public BCheckBox(SwingCtx sc, ActionListener al, String key) {
      this.sc = sc;
      this.key = key;
      if (al != null) {
         this.addActionListener(al);
      }
   }
   
   public void guiUpdate() {
      if (key != null) {
         this.setText(sc.getResString(key));
         this.setToolTipText(sc.getResString(key + ".tip"));
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BCheckBox");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BCheckBox");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

 

}
