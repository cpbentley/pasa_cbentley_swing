package pasa.cbentley.swing.widgets.b;

import javax.swing.JTextArea;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

public class BTextNote extends JTextArea implements IMyGui {

   private String   key;

   private SwingCtx sc;

   public BTextNote(SwingCtx sc, String key) {
      super(2, 3);
      this.sc = sc;
      this.key = key;
      setBackground(null);
      setEditable(false);
      setBorder(null);
      setLineWrap(true);
      setWrapStyleWord(true);
      setFocusable(false);
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
      dc.root(this, "BTextNote");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BTextNote");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

}
