package pasa.cbentley.swing.utils;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.interfaces.IStringPrefIDable;

/**
 * {@link IStringPrefIDable}
 * 
 * @author Charles Bentley
 *
 */
public class PrefIdable implements IStringPrefIDable {

   private String           key;

   protected final SwingCtx sc;

   public PrefIdable(SwingCtx sc, String key) {
      this.sc = sc;
      this.key = key;

   }

   public String getSelectorKeyPrefID() {
      return key;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "IDPanel");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "IDPanel");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   //#enddebug

}