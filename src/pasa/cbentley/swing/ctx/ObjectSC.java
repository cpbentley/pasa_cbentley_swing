package pasa.cbentley.swing.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.LogParameters;

public class ObjectSC implements IStringable {

   protected final SwingCtx sc;

   public ObjectSC(SwingCtx sc) {
      this.sc = sc;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, ObjectSC.class, 26);
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public String toStringGetLine(int value) {
      return toStringGetUCtx().toStringGetLine(value);
   }

   public LogParameters toStringGetLine(Class cl, String method, int value) {
      return toStringGetUCtx().toStringGetLine(cl, method, value);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ObjectSC.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   //#enddebug

}
