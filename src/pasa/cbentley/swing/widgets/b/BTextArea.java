package pasa.cbentley.swing.widgets.b;

import javax.swing.JTextArea;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.widgets.TabKeyAdapter;

public class BTextArea extends JTextArea implements IMyGui {

   private SwingCtx      sc;

   private TabKeyAdapter tabKeyAdapter;

   private String        textKey;

   private String        textKeyTip;

   public String getTextKey() {
      return textKey;
   }

   public void setTextKey(String textKey) {
      this.textKey = textKey;
      guiUpdate();
   }

   public BTextArea(SwingCtx sc, int rows, int cols) {
      super(rows, cols);
      this.sc = sc;
   }

   public BTextArea(SwingCtx sc) {
      this.sc = sc;
   }

   public void enableTabFocusTraversal() {
      if (tabKeyAdapter == null) {
         tabKeyAdapter = new TabKeyAdapter(sc, this);
         this.addKeyListener(tabKeyAdapter);
      }
   }

   public void guiUpdate() {
      if (textKey != null) {
         this.setText(sc.getResString(textKey));
      }
      if (textKeyTip != null) {
         this.setToolTipText(sc.getResString(textKeyTip));
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BTextArea");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BTextArea");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

   public String getTextKeyTip() {
      return textKeyTip;
   }

   public void setTextKeyTip(String textKeyTip) {
      this.textKeyTip = textKeyTip;
   }

}
