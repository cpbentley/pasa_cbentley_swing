package pasa.cbentley.swing.imytab;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Identifies a GUI component with parent hierarchy of string identifiers.
 * 
 * Short lived object used to iterate on a {@link TabPage} path.
 * 
 * @author Charles Bentley
 *
 */
public class PageStrings implements IStringable {

   private String[] strings;

   private int      count;

   private SwingCtx sc;

   public PageStrings(SwingCtx sc, String[] strings) {
      super();
      this.sc = sc;
      this.strings = strings;
   }

   public String[] getStrings() {
      return strings;
   }

   public void reset() {
      count = 0;
   }

   /**
    * Possibly null
    * @return
    */
   public String getNextString() {
      if (count >= strings.length) {
         return null;
      }
      return strings[count++];
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PageStrings");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PageStrings");
      toStringPrivate(dc);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("count", count);
      dc.append(' ');
      dc.append('[');
      dc.append(strings, 0, strings.length, ";");
      dc.append(']');
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

}
