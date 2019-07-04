package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

/**
 * 
 * @author Charles Bentley
 *
 */
public class BRadioButton extends JRadioButton implements IMyGui {

   private SwingCtx sc;

   private String   key;

   public BRadioButton(SwingCtx sc, ActionListener al, String key) {
      this.sc = sc;
      this.addActionListener(al);
      this.key = key;
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
      dc.root(this, "BRadioButton");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BRadioButton");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
