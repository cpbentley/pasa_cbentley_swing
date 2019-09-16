package pasa.cbentley.swing.widgets.b;

import javax.swing.JLabel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

public class BLabelHTML extends JLabel implements IMyGui {

   private SwingCtx sc;

   private String   key;

   private int      textSize = 14;

   private String   hexColor = "F00019";

   public String getKey() {
      return key;
   }

   public void setTextKey(String key) {
      this.key = key;
      this.setText(key);
   }

   public BLabelHTML(SwingCtx sc) {
      this.sc = sc;

   }

   public void guiUpdate() {
      StringBBuilder sb = new StringBBuilder(sc.getUCtx());
      sb.append("<html> <font size=\"");
      sb.append(textSize);
      sb.append("\" color=\"");
      sb.append(hexColor);
      sb.append("\">");
      sb.append(sc.getResString(key));
      sb.append("</font></html>");
      setText(sb.toString());
   }
   
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BLabelHTML");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BLabelHTML");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug
   

}
