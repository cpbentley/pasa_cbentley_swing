package pasa.cbentley.swing.widgets;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToStrings;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class KeyedSentence implements IStringable {

   private static final int KEY       = 0;

   private static final int SEP_DOT   = 2;

   private static final int SEP_SPACE = 1;

   private IntToStrings     data;

   private String           keyToolTip;

   private int              nextSeparator;

   private SwingCtx         sc;

   public KeyedSentence(SwingCtx sc) {
      this(sc, 5);
   }

   public KeyedSentence(SwingCtx sc, int units) {
      this.sc = sc;
      data = new IntToStrings(sc.getUCtx(), units);
   }

   public KeyedSentence c(char c) {
      data.add(1, String.valueOf(c));
      return this;
   }

   public KeyedSentence dot() {
      data.add(SEP_SPACE, "");
      return this;
   }

   public String getKeyToolTip() {
      return keyToolTip;
   }

   public String getSentence() {
      StringBBuilder sb = new StringBBuilder(sc.getUCtx());
      for (int i = 0; i < data.getSize(); i++) {
         if (data.getInt(i) == KEY) {
            sb.append(sc.getResString(data.getString(i)));
         } else {
            //depending on type.. add a space or a dot

            sb.append(data.getString(i));
         }
      }
      return sb.toString();
   }

   public String getToolTip() {
      return sc.getResString(keyToolTip);
   }

   public boolean hasToolTip() {
      return keyToolTip != null;
   }

   public KeyedSentence i(int i) {
      data.add(1, String.valueOf(i));
      return this;
   }

   public KeyedSentence key(String key) {
      data.add(key);
      return this;
   }

   public KeyedSentence s(String s) {
      data.add(1, s);
      return this;
   }

   public void setKeyToolTip(String keyToolTip) {
      this.keyToolTip = keyToolTip;
   }

   public KeyedSentence space() {
      data.add(SEP_SPACE, "");
      return this;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "KeyedSentence");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "KeyedSentence");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
