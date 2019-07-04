package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

public class BMenuItem extends JMenuItem implements IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -643480505038526391L;

   private int               commandID        = -1;

   private String            key;

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
      return sc.getUCtx();
   }
   //#enddebug

}
