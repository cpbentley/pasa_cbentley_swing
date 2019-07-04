package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionListener;

import javax.swing.JRadioButtonMenuItem;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

public class BRadioButtonMenuItem extends JRadioButtonMenuItem implements IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -290728536896566282L;

   private String            key;

   private SwingCtx          sc;

   public BRadioButtonMenuItem(SwingCtx sc, ActionListener al, String key) {
      this.sc = sc;
      if (al != null) {
         this.addActionListener(al);
      }
      this.key = key;
   }

   public void guiUpdate() {
      if (key != null) {
         setText(sc.getResString(key));
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BRadioButtonMenuItem");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BRadioButtonMenuItem");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

   private void toStringPrivate(Dctx dc) {

   }

}
