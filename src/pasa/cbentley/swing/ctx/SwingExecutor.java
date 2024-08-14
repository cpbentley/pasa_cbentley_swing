package pasa.cbentley.swing.ctx;

import javax.swing.SwingUtilities;

import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;

public class SwingExecutor extends ObjectSC implements IExecutor {

   public SwingExecutor(SwingCtx sc) {
      super(sc);
   }

   public void executeMainLater(Runnable run) {
      sc.executeLaterInUIThread(run);
   }

   public void executeMainLater(Runnable run, int millis) {
      sc.executeLaterInUIThread(run, millis);
   }

   public void executeMainNow(Runnable run) {
      sc.execute(run);
   }

   public void executeWorker(Runnable run) {
      sc.getExecutorService().execute(run);
   }

   public boolean isMainThread() {
      return SwingUtilities.isEventDispatchThread();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, SwingExecutor.class, 35);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, SwingExecutor.class, 35);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
