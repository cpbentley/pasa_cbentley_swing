package pasa.cbentley.swing.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;

public class SwingExecutor implements IExecutor {

   protected final SwingCtx sc;

   public SwingExecutor(SwingCtx sc) {
      this.sc = sc;

   }

   public void executeWorker(Runnable run) {
      sc.getExecutorService().execute(run);
   }

   public void executeMainNow(Runnable run) {
      sc.execute(run);
   }

   public void executeMainLater(Runnable run) {
      sc.executeLaterInUIThread(run);
   }

   public void executeMainLater(Runnable run, int millis) {
      sc.executeLaterInUIThread(run, millis);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "SwingExecutor");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SwingExecutor");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   //#enddebug

}
